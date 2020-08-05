package com.cofedistrict.ui.features.store.cart.adapter.holder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.features.store.presentation.cart.adapter.item.ProductItem
import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.features.store.presentation.cart.adapter.ChangeListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_store_cart_product.view.*

class ProductHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(storeProductItem: StoreProductItem, listener: ChangeListener) {
        val productItem = (storeProductItem as ProductItem).productItem

        if (!productItem.product.images.isNullOrEmpty()
                && productItem.product.images[0].isNotEmpty()) {
            Picasso.get().load(productItem.product.images[0]).into(itemView.productImage)
        }

        itemView.productName.text =productItem.product.name
        itemView.productDetails.text = productItem.product.description

        itemView.quantity.text = productItem.quantity.toString()


        itemView.price.text = productItem.product.getProductActualPrice().toString()

        itemView.productAdd.setOnClickListener {

//            if (productItem.quantity < productItem.product.stock()!!) {
                listener.onAddClick(productItem.product.id)
//            } else {
//
//                Toast.makeText(itemView.context,  itemView.context.getString(R.string.cofe_store_out_stock), Toast.LENGTH_SHORT).show()
//            }

        }

        itemView.productMinus.setOnClickListener {
            listener.onMinusClick(productItem.product.id)
        }

    }
}