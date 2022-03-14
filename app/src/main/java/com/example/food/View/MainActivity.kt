package com.example.food.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.food.R
import com.example.food.Repository.FoodRepository
import com.example.food.ViewModel.FoodViewModel
import com.example.food.ViewModel.FoodViewModelProviderFactory
import com.example.food.db.FoodDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: FoodViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView:BottomNavigationView=findViewById(R.id.bottomNavigationView)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHostFragment.navController
        navView.setupWithNavController(navController)
        val foodRepository=FoodRepository(FoodDatabase(this))
        val viewModelProviderFactory=FoodViewModelProviderFactory(foodRepository)
        viewModel=ViewModelProvider(this,viewModelProviderFactory).get(FoodViewModel::class.java)
    }
}