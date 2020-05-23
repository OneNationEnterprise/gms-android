package com.gymapp.helper.modal.phoneprefix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gymapp.R
import com.gymapp.main.data.model.country.Country
import kotlinx.android.synthetic.main.item_bottomsheet_phone_prefix.view.*

class PhonePrefixAdapter(var countries: List<Country>, val listener: PhonePrefixSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PrefixHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bottomsheet_phone_prefix, parent, false))
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as PrefixHolder
        viewHolder.bindView(countries[position], listener)
    }

    class PrefixHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(country: Country, listener: PhonePrefixSelectedListener) {
            itemView.countryNameTv.text = country.isoCode
            itemView.countryDialTv.text = country.dialCode

            Glide.with(itemView.context).load(country.flagPhoto).into(itemView.countryFlagIv)

            itemView.prefixContainer.setOnClickListener {
                listener.dialCodeSelected(country)
            }

        }

    }

}