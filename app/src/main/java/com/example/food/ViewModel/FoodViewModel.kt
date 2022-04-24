package com.example.food.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.Model.CategoryFood
import com.example.food.Model.Meal
import com.example.food.Model.RandomMeat
import com.example.food.Repository.FoodRepository
import com.example.food.Util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class FoodViewModel(val foodRepository: FoodRepository):ViewModel() {
    val Tag = "A"
    val searchMeat: MutableLiveData<Resource<RandomMeat>> = MutableLiveData()
    val filter: MutableLiveData<Resource<RandomMeat>> = MutableLiveData()
    val Details: MutableLiveData<Resource<RandomMeat>> = MutableLiveData()
    val CategoryFood: MutableLiveData<Resource<CategoryFood>> = MutableLiveData()
    var RandomResponse: RandomMeat? = null

    init {
        //  getRandomMeat()
        getCategory()
        getfilter("Beef")
    }

    fun getsearchMeat(search: String) = viewModelScope.launch {
        searchMeat.postValue(Resource.Loading())
        val response = foodRepository.getsearchMeat(search)
        searchMeat.postValue(handleRandomFood(response))
    }

    fun getCategory() = viewModelScope.launch {
        CategoryFood.postValue(Resource.Loading())
        val response = foodRepository.getCategory()
        CategoryFood.postValue(handleCategoryFood(response))
    }

    fun getfilter(category: String) = viewModelScope.launch {
        filter.postValue(Resource.Loading())
        val response = foodRepository.getfilter(category)
        filter.postValue(handlefilterFood(response))
    }

    fun getDetails(id: String) = viewModelScope.launch {
        Details.postValue(Resource.Loading())
        val response = foodRepository.getDetails(id)
        Details.postValue(handleDetailsFood(response))
    }

    private fun handleDetailsFood(response: Response<RandomMeat>): Resource<RandomMeat>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handlefilterFood(response: Response<RandomMeat>): Resource<RandomMeat>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleCategoryFood(response: Response<CategoryFood>): Resource<CategoryFood> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleRandomFood(response: Response<RandomMeat>): Resource<RandomMeat> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


     fun saveFood(meal: Meal)=viewModelScope.launch {
         foodRepository.upsert(meal)
     }

    fun getAllfood()=foodRepository.getAllFood()

    fun deleteFood(meal: Meal)=viewModelScope.launch {
        foodRepository.deleteFood(meal)
    }

}


