package com.example.testrecipeapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.testrecipeapp.MainActivity
import com.example.testrecipeapp.R
import com.example.testrecipeapp.databinding.FragmentFavoriteBinding
import com.example.testrecipeapp.databinding.FragmentHomeBinding
import com.example.testrecipeapp.ui.adapters.CategoryAdapter
import com.example.testrecipeapp.ui.adapters.FavoriteAdapter
import com.example.testrecipeapp.ui.adapters.MealAdapter
import com.example.testrecipeapp.ui.details.DetailsViewModel
import com.example.testrecipeapp.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home.*


class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: DetailsViewModel
    lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).detailsViewModel
initFavoriteAdapter()
        observeFavorites()
        favoriteAdapter.setOnItemClickListener {
            val bundle = bundleOf("mealId" to it.idMeal)
            view.findNavController().navigate(
                R.id.action_favoriteFragment_to_detailsFragment,
                bundle
            )
        }
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) : Boolean{
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
              val meal =  favoriteAdapter.differ.currentList[position]
                viewModel.deleteMeal(meal)
                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.insertMeal(meal)
                    }
                    show()
                }

            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }
    private fun initFavoriteAdapter() {
        favoriteAdapter = FavoriteAdapter()
        rv_favorites.apply {
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
    private fun observeFavorites() {
      viewModel.getSavedMeal().observe(viewLifecycleOwner, Observer { meal->
          favoriteAdapter.differ.submitList(meal)
      })
    }

}