package com.example.testrecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testrecipeapp.data.api.RecipeRepository
import com.example.testrecipeapp.data.db.RecipeDatabase
import com.example.testrecipeapp.databinding.ActivityMainBinding
import com.example.testrecipeapp.ui.details.DetailsViewModel
import com.example.testrecipeapp.ui.details.DetailsViewModelProviderFactory
import com.example.testrecipeapp.ui.home.HomeViewModel
import com.example.testrecipeapp.ui.home.HomeViewModelProviderFactory
import com.example.testrecipeapp.ui.search.SearchViewModel
import com.example.testrecipeapp.ui.search.SearchViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val mBinding get() = _binding!!
    lateinit var repository: RecipeRepository
    val homeViewModel: HomeViewModel by lazy {
        val homeViewModelProviderFactory = HomeViewModelProviderFactory()
        ViewModelProvider(this, homeViewModelProviderFactory) [HomeViewModel::class.java]
    }
    val searchViewModel: SearchViewModel by lazy{
        val searchViewModelProviderFactory = SearchViewModelProviderFactory()
        ViewModelProvider(this, searchViewModelProviderFactory) [SearchViewModel::class.java]
    }
    val detailsViewModel: DetailsViewModel by lazy{
        val detailsViewModelProviderFactory = DetailsViewModelProviderFactory(repository)
        ViewModelProvider(this, detailsViewModelProviderFactory) [DetailsViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        repository = RecipeRepository(RecipeDatabase.getInstance(this))
        CoroutineScope(Dispatchers.Main).launch{
            delay(5000)
            _binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(mBinding.root)
bottom_nav_menu.setupWithNavController(
    navController = nav_host_fragment.findNavController()
) }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}