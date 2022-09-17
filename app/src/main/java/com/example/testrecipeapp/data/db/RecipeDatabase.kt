package com.example.testrecipeapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testrecipeapp.models.*

@Database(entities = [ MealX::class], version =1 , exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao
    companion object {
        @Volatile
        var INSTANCE: RecipeDatabase? = null

        @Synchronized
        fun getInstance(context: Context):RecipeDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as RecipeDatabase
        }
    }
}