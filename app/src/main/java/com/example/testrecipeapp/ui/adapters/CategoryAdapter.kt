package com.example.testrecipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testrecipeapp.R
import com.example.testrecipeapp.models.CategoryX
import kotlinx.android.synthetic.main.item_rv_main_category.*
import kotlinx.android.synthetic.main.item_rv_main_category.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    inner class CategoryViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    private val differCallback = object: DiffUtil.ItemCallback<CategoryX>(){
        override fun areItemsTheSame(oldItem: CategoryX, newItem: CategoryX): Boolean {
            return oldItem.idCategory ==newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: CategoryX, newItem: CategoryX): Boolean {
            return oldItem==newItem
        }

    }
    val differ= AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_rv_main_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(category.strCategoryThumb).into(img_dish)
            tv_dish_name.text= category.strCategory
            setOnClickListener{
                onItemClickListener?.let{
                    it(category)
                        illumination.visibility = View.VISIBLE
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
private var onItemClickListener: ((CategoryX) -> Unit)? = null
    fun setOnItemClickListener (listener: (CategoryX) -> Unit) {
        onItemClickListener=listener
    }

}