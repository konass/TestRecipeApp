package com.example.testrecipeapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrecipeapp.data.api.RecipeRepository
import com.example.testrecipeapp.data.api.RetrofitInstance
import com.example.testrecipeapp.models.DetailMeal
import com.example.testrecipeapp.models.MealX
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailsViewModel(private val repository: RecipeRepository): ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<MealX>()
    private var favoritesLiveData = MutableLiveData<List<MealX>>()
init{
    getMealDetail("")
}
    fun getMealDetail(id:String){
        RetrofitInstance.api.getSpecificItem(id).enqueue(object : retrofit2.Callback<DetailMeal>{
            override fun onResponse(call: Call<DetailMeal>, response: Response<DetailMeal>) {
                if(response.body() != null){
                   mealDetailsLiveData.value = response.body()!!.meals[0]

                }
                else
                    return
            }

            override fun onFailure(call: Call<DetailMeal>, t: Throwable) {
                Log.d("MyLog", t.message.toString())
            }

        })
    }

    fun observerMealDetailsLiveData(): LiveData<MealX> {
        return mealDetailsLiveData
    }
    fun insertMeal (meal: MealX) {
        viewModelScope.launch {   repository.addToFavorite(meal) }
    }
 fun deleteMeal(meal: MealX) {
     viewModelScope.launch { repository.deleteFromFavorite(meal) }
 }
    fun getSavedMeal() = repository.getFavoriteMeals()
}