package com.gymapp.features.gymdetail.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.gym.GymClassCategoriesQuery
import com.gymapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_gym_detail_class_category.view.*

class ClassCategoriesAdapter(
    var categories: List<GymClassCategoriesQuery.List?>,
    val categorySelectedListener: CategorySelectedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return StoreCategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gym_detail_class_category, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StoreCategoryViewHolder).bindView(categories[position], categorySelectedListener)
    }

}

class StoreCategoryViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        list: GymClassCategoriesQuery.List?,
        categorySelectedListener: CategorySelectedListener
    ) {

        if (list == null) {
            return
        }

        if (!list.image.isNullOrEmpty()) {
            Picasso.get().load(list.image).into(itemView.categoryPhotoIv)
        }

        itemView.categoryNameTv.text = list.name

        itemView.classCategoryContainer.setOnClickListener {
            categorySelectedListener.onCategorySelected(list)
        }
    }
}

