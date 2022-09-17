package com.example.testrecipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testrecipeapp.R
import com.example.testrecipeapp.models.Meal
import com.example.testrecipeapp.models.MealX
import kotlinx.android.synthetic.main.item_rv_main_category.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){
    inner class FavoriteViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    private val differCallback = object: DiffUtil.ItemCallback<MealX>(){
        override fun areItemsTheSame(oldItem: MealX, newItem: MealX): Boolean {
            return oldItem.idMeal ==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealX, newItem: MealX): Boolean {
            return oldItem==newItem
        }

    }
    val differ= AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_rv_sub_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(meal.strMealThumb).into(img_dish)
            tv_dish_name.text= meal.strMeal
            setOnClickListener{
                onItemClickListener?.let{
                    it(meal)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener: ((MealX) -> Unit)? = null
    fun setOnItemClickListener (listener: (MealX) -> Unit) {
        onItemClickListener=listener
    }

}