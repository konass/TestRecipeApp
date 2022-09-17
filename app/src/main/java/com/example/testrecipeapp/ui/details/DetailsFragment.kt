package com.example.testrecipeapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.testrecipeapp.MainActivity
import com.example.testrecipeapp.R
import com.example.testrecipeapp.databinding.FragmentDetailsBinding
import com.example.testrecipeapp.ui.home.HomeViewModel


class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    private val bundleArgs: DetailsFragmentArgs by navArgs()
    lateinit var viewModel: DetailsViewModel
    private lateinit var youtubeLink: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).detailsViewModel
        val mealArg = bundleArgs.mealId
        viewModel.getMealDetail(mealArg)
mealObserver()
        onYoutubeImageClick()
        backClick()
    }
    private fun mealObserver (){
        viewModel.observerMealDetailsLiveData().observe(viewLifecycleOwner, Observer {meal->
            meal.strMealThumb.let{
                Glide.with(this).load(meal.strMealThumb).into(binding.imgMealDetail)
            }
            binding.collapsingToolbar.title= meal.strMeal
            binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
            binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
            binding.tvCategory.text=meal.strCategory
            binding.tvArea.text = meal.strArea
            binding.tvInstructions.text=meal.strInstructions
            youtubeLink= meal.strYoutube!!

            binding.btnAddToFav.setOnClickListener {
                viewModel.insertMeal(meal)
            }
        } )
    }
    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
private fun backClick() {
    binding.btnBack.setOnClickListener{
        findNavController().popBackStack()
    }
}
    }
