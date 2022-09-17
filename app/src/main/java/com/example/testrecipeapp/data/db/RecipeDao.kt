package com.example.testrecipeapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testrecipeapp.models.CategoryX
import com.example.testrecipeapp.models.Meal
import com.example.testrecipeapp.models.MealX

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:MealX)

    //Удаление
    @Delete
    suspend fun delete(meal:MealX)

    //Запрос
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<MealX>>
}