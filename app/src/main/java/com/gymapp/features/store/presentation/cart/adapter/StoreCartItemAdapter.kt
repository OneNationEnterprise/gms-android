package com.gymapp.features.store.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cofedistrict.ui.features.store.cart.adapter.holder.ProductHolder
import com.gymapp.features.store.presentation.cart.adapter.holder.SubtotalHolder
import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.R
import com.gymapp.features.store.presentation.cart.adapter.holder.HeaderHolder
import com.gymapp.helper.STORE_CART_ITEMS

class StoreCartItemAdapter(var productsList: MutableList<StoreProductItem>, val listener: ChangeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            STORE_CART_ITEMS.SECTION_HEADER.ordinal -> HeaderHolder(
                LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store_cart_header, parent, false))
            STORE_CART_ITEMS.SELECTED_PRODUCT.ordinal -> ProductHolder(
                LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store_cart_product, parent, false))
            STORE_CART_ITEMS.SECTION_SUBTOTAL.ordinal -> SubtotalHolder(
                LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store_cart_subtotal, parent, false))
            else -> {
                HeaderHolder(
                    LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_deliver_row, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return productsList[position].getType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
           STORE_CART_ITEMS.SECTION_HEADER.ordinal -> {
                val viewHolder = holder as HeaderHolder
                viewHolder.bindView(productsList[position])
            }
            STORE_CART_ITEMS.SELECTED_PRODUCT.ordinal -> {
                val viewHolder = holder as ProductHolder
                viewHolder.bindView(productsList[position], listener)
            }
            STORE_CART_ITEMS.SECTION_SUBTOTAL.ordinal -> {
                val viewHolder = holder as SubtotalHolder
                viewHolder.bindView(productsList[position])
            }
            else -> {
            }
        }
    }

    fun updateList(recycleViewList: MutableList<StoreProductItem>) {
        productsList.clear()
        productsList.addAll(recycleViewList)
        notifyDataSetChanged()
    }
}