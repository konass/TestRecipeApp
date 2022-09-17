package com.example.testrecipeapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testrecipeapp.data.api.RecipeRepository

class DetailsViewModelProviderFactory(private val repository: RecipeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository ) as T
    }
}