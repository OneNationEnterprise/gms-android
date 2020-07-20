package com.gymapp.features.mapview.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.main.data.model.gym.Gym
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mapview_gym.view.*
import java.util.*

class MapViewGymListAdapter(var gyms: MutableList<Gym>) :
    RecyclerView.Adapter<GymViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        return GymViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mapview_gym, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gyms.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        holder.bindView(gyms[position])
    }

    fun updateList(gyms: MutableList<Gym>) {
        this.gyms.clear()
        this.gyms.addAll(gyms)
        notifyDataSetChanged()
    }

}

class GymViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(gym: Gym) {

        itemView.gymNameTv.text = gym.name


        if (gym.distance != null) {
            itemView.gymDistanceTv.text = itemView.context.getString(
                R.string.mapview_gym_distance, String.format(
                    Locale.US, "%.2f", gym.distance / 1000
                )
            )
        }
        itemView.brandLocationContainer.setOnClickListener {
//            itemClickListener.onLocationSelected(location)
        }

        if (!gym.brand.logo.isNullOrEmpty()) {
            Picasso.get().load(gym.brand.logo).into(itemView.brandLogoIv)
        }
    }
}



