package com.gymapp.features.store.presentation.cart.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.features.store.presentation.cart.adapter.item.SubtotalItem
import kotlinx.android.synthetic.main.item_store_cart_subtotal.view.*

class SubtotalHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(storeProductItem: StoreProductItem) {
        val subtotal = storeProductItem as SubtotalItem

        itemView.value.text =(subtotal.totalValue).toString()
    }
}