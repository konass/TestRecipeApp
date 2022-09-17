package com.example.testrecipeapp.data.api

import com.example.testrecipeapp.data.db.RecipeDatabase
import com.example.testrecipeapp.models.MealX

class RecipeRepository(val db: RecipeDatabase) {
    val mealDao = db.getRecipeDao()
fun getFavoriteMeals() = mealDao.getAllMeals()
    suspend fun addToFavorite( meal: MealX)= mealDao.upsert(meal)
    suspend fun deleteFromFavorite (meal: MealX)= mealDao.delete(meal)
}