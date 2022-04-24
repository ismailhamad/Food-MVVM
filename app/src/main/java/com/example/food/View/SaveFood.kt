package com.example.food.View

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Adapter.saveAdapter
import com.example.food.R
import com.example.food.ViewModel.FoodViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_save_food.*


class saveFood : Fragment(R.layout.fragment_save_food) {
lateinit var viewModel: FoodViewModel
lateinit var savefood: saveAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupReceylview()
        viewModel.getAllfood().observe(viewLifecycleOwner, Observer { it->
            savefood.differ.submitList(it)
        })
        savefood.setOnItemClickListener { Meal->
            val Bundle=Bundle().apply {
                putSerializable("details",Meal)
            }
            findNavController().navigate(R.id.action_saveFood_to_details,Bundle)
        }

        val itemTouchHelperCallback=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val food=savefood.differ.currentList[position]
                if (direction == ItemTouchHelper.LEFT){
                    viewModel.deleteFood(food)
                    Snackbar.make(view, "Deleted successfully", Snackbar.LENGTH_LONG).apply {
                        animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                        setBackgroundTint(Color.RED)
                        setTextColor(Color.parseColor("#FFFFFF"))
                        show()
                    }.setAction("undo",{
                        viewModel.saveFood(food)
                    })

                }else if (direction == ItemTouchHelper.RIGHT){

                }


            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(save_rv)
        }


    }

    private fun setupReceylview() {
     savefood = saveAdapter()
        save_rv.apply {
        adapter=savefood
            layoutManager=LinearLayoutManager(context)
        }

    }


}