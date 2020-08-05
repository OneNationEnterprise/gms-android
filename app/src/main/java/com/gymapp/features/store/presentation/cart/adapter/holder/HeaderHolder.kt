package com.gymapp.features.store.presentation.cart.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.features.store.presentation.cart.adapter.item.BrandItem
import kotlinx.android.synthetic.main.item_store_cart_header.view.*

class HeaderHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(headerItem: StoreProductItem) {
        val brand = headerItem as BrandItem

        itemView.headerText.text = brand.brandName
    }
}