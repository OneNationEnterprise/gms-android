package com.gymapp.features.store.presentation.homepage.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.store.data.HomepageSection
import com.gymapp.features.store.data.model.Category
import com.gymapp.features.store.data.model.Product
import com.gymapp.features.store.data.model.Store
import com.gymapp.features.store.data.model.StoreImage
import com.gymapp.features.store.presentation.homepage.StoreItemSelectedListener
import com.gymapp.helper.HomepageSectionType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_store_brand.view.*
import kotlinx.android.synthetic.main.item_store_homepage_category.view.*
import kotlinx.android.synthetic.main.item_store_homepage_header.view.*
import kotlinx.android.synthetic.main.item_store_homepage_header_single_item.view.headerPhotoIv
import kotlinx.android.synthetic.main.item_store_product.view.*
import kotlin.math.roundToInt

class StoreHomepageAdapter(
    var items: List<HomepageSection>,
    val listener: StoreItemSelectedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            HomepageSectionType.IMAGE.ordinal -> {
                if (items.size > 1) {
                    StoreHeaderViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_store_homepage_header, parent, false)
                    )
                } else {
                    StoreHeaderViewSingleItemHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_store_homepage_header_single_item, parent, false)
                    )
                }
            }
            HomepageSectionType.STORE.ordinal -> {
                StoreBrandViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_store_brand, parent, false)
                )
            }
            HomepageSectionType.CATEGORY.ordinal -> {
                StoreCategoryViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_store_homepage_category, parent, false)
                )
            }
            HomepageSectionType.PRODUCT.ordinal -> {
                StoreProductViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_store_product, parent, false)
                )
            }
            else -> {
                EmptyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_store_homepage_empty, parent, false)
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType().ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (items[position].getType()) {
            HomepageSectionType.IMAGE -> {

                if (items.size == 1) {
                    (holder as StoreHeaderViewSingleItemHolder).bindView(items[position] as StoreImage)
                } else {
                    (holder as StoreHeaderViewHolder).bindView(
                        items[position] as StoreImage,
                        position,
                        position == items.size - 1
                    )
                }

            }
            HomepageSectionType.STORE -> {
                (holder as StoreBrandViewHolder).bindView(
                    items[position] as Store,
                    position,
                    position == items.size - 1,
                    listener
                )
            }
            HomepageSectionType.CATEGORY -> {
                (holder as StoreCategoryViewHolder).bindView(
                    items[position] as Category,
                    position,
                    position == items.size - 1,
                    listener
                )
            }
            HomepageSectionType.PRODUCT -> {
                (holder as StoreProductViewHolder).bindView(
                    items[position] as Product,
                    position,
                    position == items.size - 1
                )
            }
            else -> {
                (holder as EmptyViewHolder).bindView()
            }
        }
    }

}

class StoreHeaderViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(
        item: StoreImage,
        positon: Int,
        lastItem: Boolean
    ) {
        val imageUrl = item.image
        Picasso.get().load(imageUrl).into(itemView.headerPhotoIv)
        itemView.headerPhotoIv.setOnClickListener {}

        val containerLayaoutParams =
            itemView.photoContainerHeader.layoutParams as (RecyclerView.LayoutParams)

        if (positon == 0) {
            containerLayaoutParams.marginStart =
                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_start_first_item))
        }

        if (lastItem) {
            containerLayaoutParams.marginEnd =
                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_end_last_item))
        }
    }
}

class StoreHeaderViewSingleItemHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(item: StoreImage) {
        val imageUrl = item.image

        if (imageUrl.isNotEmpty()) {
            Picasso.get().load(imageUrl).into(itemView.headerPhotoIv)
        }
        itemView.headerPhotoIv.setOnClickListener {}
    }
}

class StoreBrandViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        asBrand: Store,
        position: Int,
        lastItem: Boolean,
        listener: StoreItemSelectedListener
    ) {
        if (!asBrand.logo.isNullOrEmpty()) {
            Picasso.get().load(asBrand.logo).into(itemView.brandLogoIv)
        }

        val containerLayaoutParams =
            itemView.brandContainerLl.layoutParams as (RecyclerView.LayoutParams)

        if (position > 0) {
            containerLayaoutParams.marginStart =
                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_start_next_item))
        }

        if (lastItem) {
            containerLayaoutParams.marginEnd =
                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_end_last_item))
        }

        itemView.brandLogoIv.setOnClickListener {
            listener.hasSelectedStore(asBrand.id, asBrand.name)
        }
    }
}

class StoreCategoryViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        asCategory: Category,
        positon: Int,
        lastItem: Boolean,
        listener: StoreItemSelectedListener
    ) {

        if (!asCategory.image.isNullOrEmpty()) {
            Picasso.get().load(asCategory.image).into(itemView.categoryPhotoIv)
        }
        itemView.categoryNameTv.text = asCategory.name

        val containerLayaoutParams =
            itemView.photoContainerCategory.layoutParams as (RecyclerView.LayoutParams)

        if (positon > 0) {
            containerLayaoutParams.marginStart =
                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_start_next_item))
        }

        if (lastItem) {
            containerLayaoutParams.marginEnd =
                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_end_last_item))
        }

        itemView.categoryPhotoIv.setOnClickListener {
            listener.hasSelectedCategory(asCategory.id, asCategory.name)
        }
    }
}

class StoreProductViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        product: Product,
        position: Int,
        lastItem: Boolean
    ) {
        val containerLayaoutParams =
            itemView.card_view_outer.layoutParams as (RecyclerView.LayoutParams)

//        if (position > 0) {
//            containerLayaoutParams.marginStart =
//                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_start_next_item))
//        }

//        if (lastItem) {
//            containerLayaoutParams.marginEnd =
//                (itemView.context.resources.getDimensionPixelSize(R.dimen.margin_end_last_item))
//        }

        containerLayaoutParams.width = itemView.context.resources.getDimensionPixelSize(R.dimen.product_card_width)

        ///////
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

class EmptyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView() {}
}
