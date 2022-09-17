package com.example.testrecipeapp.ui.search

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testrecipeapp.MainActivity
import com.example.testrecipeapp.R
import com.example.testrecipeapp.databinding.FragmentHomeBinding
import com.example.testrecipeapp.databinding.FragmentSearchBinding
import com.example.testrecipeapp.ui.adapters.CategoryAdapter
import com.example.testrecipeapp.ui.adapters.MealAdapter
import com.example.testrecipeapp.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: SearchViewModel
    lateinit var mealAdapter: MealAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealAdapter()
        viewModel = (activity as MainActivity).searchViewModel
        var job: Job? = null
        ed_search.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job= MainScope().launch {
                delay(500L)
                text?.let{
                    if (it.toString().isNotEmpty()){
                        viewModel.searchMeal(it.toString())
                    }
                }
            }
        }
        observerMealsItem()
        mealAdapter.setOnItemClickListener {
            val bundle = bundleOf("mealId" to it.idMeal)
            view.findNavController().navigate(
                R.id.action_searchFragment_to_detailsFragment,
                bundle
            )
        }
    }
    private fun initMealAdapter() {
        mealAdapter = MealAdapter()
       search_meal.apply{
            adapter = mealAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
    private fun observerMealsItem() {
        viewModel.observeMealsItem().observe(viewLifecycleOwner, Observer { meals->
            mealAdapter.differ.submitList(meals)
        })
    }
}