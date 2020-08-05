package com.gymapp.features.store.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_viewpager_product_image.view.*

class ProductImagesPagerAdapter(val images: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager_product_image, parent, false)) {}
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imageUrl = images[position]

        Picasso.get().load(imageUrl).into(holder.itemView.productImageIv)
    }
}