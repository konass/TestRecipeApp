package com.example.testrecipeapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrecipeapp.data.api.RetrofitInstance
import com.example.testrecipeapp.models.Meal
import com.example.testrecipeapp.models.MealItems
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var mealByName = MutableLiveData<List<Meal>>()
    init{
searchMeal("")
    }
    fun searchMeal (strName: String) = viewModelScope.launch {
        RetrofitInstance.api.searchMeal(strName).enqueue(object : Callback<MealItems> {
            override fun onResponse(call: Call<MealItems>, response: Response<MealItems>) {
                response.body().let { mealItems ->
                    mealByName.postValue(mealItems?.meals)
                }
            }

            override fun onFailure(call: Call<MealItems>, t: Throwable) {
                Log.e("MyLog", t.message.toString())
            }

        })
    }
    fun observeMealsItem() : LiveData<List<Meal>> {
        return mealByName
    }
}