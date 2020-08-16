package com.gymapp.features.store.presentation.payment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.R
import com.gymapp.features.store.presentation.cart.adapter.item.ProductItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_store_payment_product.view.*

class ItemStorePaymentAdapter(var productsList: MutableList<ProductItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ProductHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store_payment_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return productsList[position].getType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewHolder = holder as ProductHolder
        viewHolder.bindView(productsList[position])
    }


    class ProductHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(productItem: ProductItem) {
            if (!productItem.productItem.product.images.isNullOrEmpty()
                && productItem.productItem.product.images[0].isNotEmpty()
            ) {
                Picasso.get().load(productItem.productItem.product.images[0])
                    .into(itemView.productImage)
            }

            itemView.productName.text =
                "${productItem.productItem.quantity.toString()} x ${productItem.productItem.product.name}"

            itemView.price.text = productItem.productItem.product.getProductActualPrice().toString()
        }
    }

}