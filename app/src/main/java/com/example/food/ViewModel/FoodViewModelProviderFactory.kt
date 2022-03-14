package com.example.food.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.food.Repository.FoodRepository

class FoodViewModelProviderFactory(val foodRepository: FoodRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FoodViewModel(foodRepository) as T
    }
}