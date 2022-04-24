package com.example.food.View

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adapter.CategoryAdapter
import com.example.food.Adapter.FoodAdapter
import com.example.food.Model.Category
import com.example.food.R
import com.example.food.Util.Resource
import com.example.food.ViewModel.FoodViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_food.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Home : Fragment(R.layout.fragment_home) {
lateinit var viewModel: FoodViewModel
lateinit var foodAdapter: FoodAdapter
lateinit var categoryAdapter: CategoryAdapter
lateinit var Category: Category
    val TAG = "BreakingNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupReceylview()
        setupReceylviewC()
        titleRE.text="Beef"
        var job:Job?=null
        serach_food.text.clear()
        serach_food.addTextChangedListener { editable->
            job?.cancel()
            job= MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.getsearchMeat(editable.toString())

                        viewModel.searchMeat.observe(viewLifecycleOwner, Observer {response->
                            when(response) {
                                is Resource.Success -> {
                                    response.data?.let { Random ->
                                        foodAdapter.differ.submitList(null)
                                        foodAdapter.differ.submitList(Random.meals)
                                        for (item in Random.meals){
                                            titleRE.text=item.strMeal
                                        }
                                      //  editable.clear()
                                    }

                                }
                                is Resource.Error -> {
                                    response.messge.let { message ->
                                        Log.e(TAG, "An error occured: $message")
                                    }
                                }
                            }
                        })


                    }


                }

            }

        }

        foodAdapter.setOnItemClickListener2 {
            viewModel.saveFood(it)
            Snackbar.make(view, "Saved successfully", Snackbar.LENGTH_LONG).apply {
                animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                setBackgroundTint(Color.parseColor("#1AD836"))
                setTextColor(Color.parseColor("#FFFFFF"))
                show()
            }
        }


        foodAdapter.setOnItemClickListener { Meal->
            val Bundle=Bundle().apply {
                putSerializable("details",Meal)
            }
            findNavController().navigate(R.id.action_home2_to_details,Bundle)
        }
        categoryAdapter.setOnItemClickListener { category ->
            categoryy=category
            titleRE.text=category.strCategory
            foodAdapter.differ.submitList(null)
            viewModel.getfilter(category.strCategory)
        }

        viewModel.filter.observe(viewLifecycleOwner, Observer { response->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { Random ->
                        foodAdapter.differ.submitList(Random.meals)

                    }
                }
                is Resource.Error -> {
                    response.messge.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
            }


        })

        viewModel.CategoryFood.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    response.data?.let { Category->
                        categoryAdapter.differ.submitList(Category.categories)
                    }
                }
                is Resource.Error -> {
                    response.messge.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
            }
        })

    }

    private fun setupReceylview() {
        foodAdapter= FoodAdapter()
        rv_food.apply {

            adapter=foodAdapter
            layoutManager= GridLayoutManager(context,2)
        }

    }
    private fun setupReceylviewC() {
        categoryAdapter= CategoryAdapter()
        recyclerView.apply {
            adapter=categoryAdapter
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)

        }

    }
    companion object{
       var categoryy:Category?=null
    }


}