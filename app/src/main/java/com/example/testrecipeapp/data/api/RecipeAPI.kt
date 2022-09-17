package com.example.testrecipeapp.data.api

import com.example.testrecipeapp.models.CategoryItems
import com.example.testrecipeapp.models.DetailMeal
import com.example.testrecipeapp.models.MealItems
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeAPI {
    @GET("categories.php")
   fun getCategoryList(): Call<CategoryItems>
   @GET("filter.php")
    fun getMealList(
       @Query("c")
       category: String
   ): Call<MealItems>
   @GET ("lookup.php")
   fun getSpecificItem(
       @Query("i")
       id: String
   ) : Call<DetailMeal>
    @GET("search.php")
    fun searchMeal(
        @Query("s")
        strName: String
    ): Call<MealItems>
}