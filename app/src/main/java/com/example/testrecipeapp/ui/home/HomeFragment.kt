package com.example.testrecipeapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testrecipeapp.MainActivity
import com.example.testrecipeapp.R
import com.example.testrecipeapp.databinding.FragmentHomeBinding
import com.example.testrecipeapp.models.DetailMeal
import com.example.testrecipeapp.models.MealX
import com.example.testrecipeapp.ui.adapters.CategoryAdapter
import com.example.testrecipeapp.ui.adapters.MealAdapter
import com.example.testrecipeapp.ui.details.DetailsFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_rv_main_category.*
import kotlinx.android.synthetic.main.item_rv_main_category.view.*


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var mealAdapter: MealAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryAdapter()
        initMealAdapter()
        viewModel = (activity as MainActivity).homeViewModel
        observerCategoriesItems()
        observerMealsItem()
        categoryAdapter.setOnItemClickListener {
            viewModel.getMealByCategory(it.strCategory)
        }
        mealAdapter.setOnItemClickListener {
            val bundle = bundleOf("mealId" to it.idMeal)
            view.findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundle
            )
        }

    }

    private fun initCategoryAdapter() {
        categoryAdapter = CategoryAdapter()

        rv_main_category.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initMealAdapter() {
        mealAdapter = MealAdapter()
        rv_sub_category.apply {
            adapter = mealAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observerCategoriesItems() {
        viewModel.observeCategoriesItem().observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.differ.submitList(categories)

        })
    }

    private fun observerMealsItem() {
        viewModel.observeMealsItem().observe(viewLifecycleOwner, Observer { meals ->
            mealAdapter.differ.submitList(meals)
        })
    }


    }
