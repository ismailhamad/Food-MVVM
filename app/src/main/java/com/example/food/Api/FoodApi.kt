package com.example.food.Api

import com.example.food.Model.CategoryFood
import com.example.food.Model.RandomMeat
import com.example.food.View.Details
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("search.php")
    suspend fun getsearchMeat(
        @Query("s")
        search:String="lak"
    ):Response<RandomMeat>
    @GET("categories.php")
    suspend fun getcategory():Response<CategoryFood>
    @GET("filter.php")
    suspend fun filterCategory(
        @Query("c")
        category:String="Seafood"
    ):Response<RandomMeat>
@GET("lookup.php")
suspend fun Details(
    @Query("i")
    id:String="1"
):Response<RandomMeat>
//    suspend fun getFoodCategory(
//        @Query("i")
//        id:Int=1)
}