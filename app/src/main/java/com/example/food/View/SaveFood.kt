package com.example.food.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adapter.saveAdapter
import com.example.food.R
import com.example.food.ViewModel.FoodViewModel
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
    }

    private fun setupReceylview() {
     savefood = saveAdapter()
        save_rv.apply {
        adapter=savefood
            layoutManager=LinearLayoutManager(context)
        }

    }


}