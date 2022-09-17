package com.example.testrecipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


data class CategoryX(

    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)