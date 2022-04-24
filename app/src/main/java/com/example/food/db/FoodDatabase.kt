package com.example.food.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.food.Model.Meal

@Database(
    entities = [Meal::class],version = 4,exportSchema = false
)

abstract class FoodDatabase:RoomDatabase() {
    abstract fun getFoodDao():FoodDao
    companion object{
        @Volatile
        private var instance:FoodDatabase?=null
        private val LOCK=Any()
        operator fun invoke(context: Context)=instance?: synchronized(LOCK){
            instance?:createDatabase(context).also{ instance=it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,FoodDatabase::class.java,
            "foood_db.db").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}