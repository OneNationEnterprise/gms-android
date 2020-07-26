package com.gymapp.features.gymdetail.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.main.data.model.gym.Amenity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_amenity.view.*

class AmenityAdapter(var amenities: List<Amenity>) :
    RecyclerView.Adapter<AmenityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenityViewHolder {
        return AmenityViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_amenity, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return amenities.size
    }

    override fun onBindViewHolder(holder: AmenityViewHolder, position: Int) {
        holder.bindView(amenities[position])
    }

}

class AmenityViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(amenity: Amenity) {

        if (!amenity.icon.isNullOrEmpty()) {
            Picasso.get().load(amenity.icon).into(itemView.iconIv)
        }
        itemView.nameTv.text = amenity.name
    }
}