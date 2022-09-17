package com.example.testrecipeapp.models

import java.io.Serializable


data class Meal(

    val categoryName: String,
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
):Serializable