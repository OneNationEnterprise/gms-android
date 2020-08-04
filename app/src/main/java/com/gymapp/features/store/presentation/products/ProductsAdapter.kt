package com.gymapp.features.store.presentation.products

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.store.data.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_store_product.view.*
import kotlin.math.roundToInt

class ProductsAdapter(var products: MutableList<Product>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ItemHolder
        viewHolder.bindView(products[position])

    }
}


class ItemHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(product: Product) {

        val imagesList = product?.images

        if (!imagesList.isNullOrEmpty()) {
            val imageUrl = imagesList[0]

            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(itemView.productImageIv)
            }
        }

        itemView.productName.text = product.name

        if (product?.salePrice != null && product.salePrice.toString()
                .toDouble() > 0
        ) {

            itemView.productPrice.text = product.salePrice.toString()

            itemView.oldProductPrice.text = product.listPrice.toString()


            itemView.oldProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            val diff =
                product?.listPrice.toString().toDouble() - product?.salePrice.toString()
                    .toDouble()

            if (diff > 0) {
                val percentage =
                    ((diff * 100) / product.listPrice.toString().toDouble()).roundToInt()

                itemView.productDiscountValue.text = "${percentage.toString()}% OFF"

                itemView.productDiscountValue.visibility = View.VISIBLE

            } else {
                itemView.productDiscountValue.visibility = View.INVISIBLE
            }

        } else {
            itemView.productDiscountValue.visibility = View.INVISIBLE

            itemView.productPrice.text = product.listPrice.toString()
        }

        itemView.cardViewInnerProductContainer.setOnClickListener {
//            storeActivityListener.openProductDetail(product)
        }
    }
}
