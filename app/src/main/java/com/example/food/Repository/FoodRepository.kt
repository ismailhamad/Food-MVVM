package com.example.food.Repository

import com.example.food.Api.RetrofitInstance
import com.example.food.Model.Meal
import com.example.food.db.FoodDatabase

class FoodRepository(val db:FoodDatabase) {
    suspend fun getsearchMeat(search:String)=RetrofitInstance.api.getsearchMeat(search)
    suspend fun getCategory()=RetrofitInstance.api.getcategory()
    suspend fun getfilter(category: String)=RetrofitInstance.api.filterCategory(category)
    suspend fun getDetails(id:String)=RetrofitInstance.api.Details(id)

    //---------------------database-------------------------------------------------/>
    suspend fun upsert(meal: Meal)=db.getFoodDao().upsert(meal)
    fun getAllFood()=db.getFoodDao().getAllfood()
    suspend fun deleteFood(meal: Meal)=db.getFoodDao().deleteFood(meal)

}