package com.gymapp.features.store.presentation.cart.adapter.item

import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.features.store.data.model.StoreCartProduct
import com.gymapp.helper.STORE_CART_PRODUCTS

class ProductItem(val productItem: StoreCartProduct) : StoreProductItem {
    override fun getType(): Int {
        return STORE_CART_PRODUCTS.PRODUCT_ROW.ordinal
    }
}