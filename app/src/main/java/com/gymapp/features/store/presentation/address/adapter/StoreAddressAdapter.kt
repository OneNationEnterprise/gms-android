package com.gymapp.features.store.presentation.address.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.main.data.model.user.AddressUser
import kotlinx.android.synthetic.main.item_store_add_address.view.*
import kotlinx.android.synthetic.main.item_store_select_address.view.*

class StoreAddressAdapter(
    val locations: List<AddressUser>,
    val addressSelectedListener: AddressSelectedListener,
    var selectedAddressId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            0 -> return AddressesListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_store_select_address, parent, false)
            )
            1 -> return AddAddressViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_store_add_address, parent, false)
            )
            2 -> {
                return EmptyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_padding_bottom, parent, false)
                )
            }
            else -> {
                return EmptyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_padding_bottom, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return locations.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position < locations.size) {
            return 0
        }

        if (position == locations.size) {
            return 1
        }

        return 2
    }

    fun setSelectedAddress(id: String) {
        selectedAddressId = id
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                val viewHolder = holder as AddressesListViewHolder
                holder.bindView(locations[position], addressSelectedListener, selectedAddressId)
            }
            1 -> {
                val viewHolder = holder as AddAddressViewHolder
                holder.bindView(addressSelectedListener)
            }
            else -> {
            }
        }
    }

    class AddressesListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(
            address: AddressUser,
            addressClickListener: AddressSelectedListener,
            selectedAddressId: String
        ) {
//            itemView.addressName.text = address.friendlyName()

            itemView.addressContainer.setOnClickListener {
                addressClickListener.onAddressSelected(address.id)
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


            if (selectedAddressId == address.id) {
                itemView.radioBtn.setImageResource(R.drawable.ic_radiobutton_iconon)
            } else {
                itemView.radioBtn.setImageResource(R.drawable.ic_radiobutton_iconoff)
            }
        }
    }

    class AddAddressViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(onAddAddressClickListener: AddressSelectedListener) {
            itemView.addNewAddressTv.setOnClickListener {
                onAddAddressClickListener.onAddNewAddressSelected()
            }

        }
    }

    class EmptyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(onAddAddressClickListener: AddressSelectedListener) {
            itemView.addNewAddressTv.setOnClickListener {
                onAddAddressClickListener.onAddNewAddressSelected()
            }

        }
    }
}