package com.gymapp.features.store.presentation.cart.adapter.item

import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.helper.STORE_CART_PRODUCTS

class BrandItem(val brandName:String) : StoreProductItem {
    override fun getType(): Int {
        return STORE_CART_PRODUCTS.BRAND_HEADER.ordinal
    }
}