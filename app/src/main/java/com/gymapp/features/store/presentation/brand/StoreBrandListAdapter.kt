package com.gymapp.features.store.presentation.brand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.store.data.model.Store
import com.gymapp.features.store.presentation.homepage.StoreItemSelectedListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_store_brand_list.view.*

class StoreBrandListAdapter(
    var items: MutableList<Store>,
    val storeItemSelectedListener: StoreItemSelectedListener
) : RecyclerView.Adapter<StoreBrandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreBrandViewHolder {
        return StoreBrandViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store_brand_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: StoreBrandViewHolder, position: Int) {
        holder.bindView(items[position], storeItemSelectedListener)
    }

    fun updateList(brandsList: MutableList<Store>) {
        items = brandsList
        notifyDataSetChanged()
    }
}


class StoreBrandViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        store: Store,
        storeItemSelectedListener: StoreItemSelectedListener
    ) {

        if (!store.logo.isNullOrEmpty()) {
            Picasso.get().load(store.logo).into(itemView.brandLogoIv)
        }

        itemView.brandLogoIv.setOnClickListener {
            storeItemSelectedListener.hasSelectedSpecificStore(storeId = store.id, pageTitle = store.name)
        }
    }
}
