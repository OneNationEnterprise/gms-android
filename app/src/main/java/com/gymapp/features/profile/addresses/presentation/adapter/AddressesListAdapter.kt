package com.gymapp.features.profile.addresses.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.profile.addresses.presentation.list.AddressesListView
import com.gymapp.main.data.model.user.AddressUser
import kotlinx.android.synthetic.main.item_add_new_address.view.*
import kotlinx.android.synthetic.main.item_address.view.*

class AddressesListAdapter(var locations: List<AddressUser>, val addressesListView: AddressesListView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            0 -> return AddressesListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_address, parent, false)
            )
            1 -> return AddAddressViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_new_address, parent, false)
            )
            else -> {
                return AddressesListViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_address, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return locations.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position < locations.size) {
            return 0
        }
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                val viewHolder = holder as AddressesListViewHolder
                holder.bindView(locations.get(position), addressesListView)
            }
            1 -> {
                val viewHolder = holder as AddAddressViewHolder
                holder.bindView(addressesListView)
            }
            else -> {
            }
        }
    }

    class AddressesListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(address: AddressUser, addressClickListener: AddressesListView) {
//            itemView.addressName.text = address.friendlyName()

            itemView.addressContainer.setOnClickListener {
                addressClickListener.onAddressClick(address.id)
            }

            if (address.dynamicData.isNullOrEmpty()) {
                return
            }

            var details = ""
            for (field in address.dynamicData!!) {
                if (!field.value.isNullOrEmpty()) {
                    details = details + field.value + ", "
                }
            }

            itemView.addressLocation.text = details.dropLast(2)
        }
    }

    class AddAddressViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(onAddAddressClickListener: AddressesListView) {
            itemView.addNewAddressTv.setOnClickListener {
                onAddAddressClickListener.onAddAddressClick()
            }

        }
    }
}