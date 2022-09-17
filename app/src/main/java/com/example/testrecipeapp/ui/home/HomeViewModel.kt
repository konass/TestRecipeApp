package com.example.testrecipeapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrecipeapp.data.api.RecipeRepository
import com.example.testrecipeapp.data.api.RetrofitInstance
import com.example.testrecipeapp.models.CategoryItems
import com.example.testrecipeapp.models.CategoryX
import com.example.testrecipeapp.models.Meal
import com.example.testrecipeapp.models.MealItems

import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
  private  var recipeCategory: MutableLiveData <List<CategoryX>> = MutableLiveData()
    private var mealByCategory = MutableLiveData<List<Meal>>()

    init{
        getCategories()
        getMealByCategory("Beef")
    }
 fun getCategories ()= viewModelScope.launch {
        RetrofitInstance.api.getCategoryList().enqueue(object: Callback<CategoryItems>{
            override fun onResponse(call: Call<CategoryItems>, response: Response<CategoryItems>) {
                response.body()?.let { categoryItems ->
                    recipeCategory.postValue(categoryItems.categories)
                }
            }

            override fun onFailure(call: Call<CategoryItems>, t: Throwable) {
                Log.d("MyLog", t.message.toString())
            }
        })
        }
  fun getMealByCategory (categoryName: String) = viewModelScope.launch {
      RetrofitInstance.api.getMealList(categoryName).enqueue(object : Callback<MealItems>{
          override fun onResponse(call: Call<MealItems>, response: Response<MealItems>) {
              response.body().let { mealItems ->
                 mealByCategory.postValue(mealItems?.meals)
              }
          }

          override fun onFailure(call: Call<MealItems>, t: Throwable) {
             Log.e("MyLog", t.message.toString())
          }

      })
  }
    fun observeCategoriesItem() : LiveData<List<CategoryX>>{
        return recipeCategory
    }
    fun observeMealsItem() : LiveData<List<Meal>>{
        return mealByCategory
    }
    }
