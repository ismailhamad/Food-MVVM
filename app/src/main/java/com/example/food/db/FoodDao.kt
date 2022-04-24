package com.example.food.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.food.Model.Meal

@Dao

interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal):Long
    @Query("Select * From food")
    fun getAllfood():LiveData<List<Meal>>
    @Delete
    suspend fun deleteFood(meal: Meal)
}