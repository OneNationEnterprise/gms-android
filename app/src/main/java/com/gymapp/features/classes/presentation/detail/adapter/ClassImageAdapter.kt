package com.gymapp.features.classes.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_class_detail.view.*

class ClassImageAdapter(var images: List<String>) :
    RecyclerView.Adapter<CollectionItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionItemViewHolder {
        return CollectionItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_class_detail, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: CollectionItemViewHolder, position: Int) {
        holder.bindView(images[position])
    }

}

class CollectionItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(imageUrl: String) {

        if (imageUrl.isNotEmpty()) {
            Picasso.get().load(imageUrl).into(itemView.classImageIv)
        }
    }
}