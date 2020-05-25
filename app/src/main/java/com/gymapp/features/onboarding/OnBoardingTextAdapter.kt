package com.gymapp.features.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import kotlinx.android.synthetic.main.item_onboarding_viewpager_text.view.*

class OnBoardingTextAdapter(private val infoText: List<Pair<String, String>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_onboarding_viewpager_text, parent, false)
        ) {}
    }

    override fun getItemCount() = infoText.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pair = infoText[position]

        holder.itemView.titleTv.text = pair.first
        holder.itemView.descriptionTv.text = pair.second
    }
}