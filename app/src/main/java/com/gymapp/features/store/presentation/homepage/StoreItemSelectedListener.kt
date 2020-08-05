package com.gymapp.features.store.presentation.homepage

import com.gymapp.features.store.data.model.Product

interface StoreItemSelectedListener {

    fun hasSelectedSeeAllStores()

    fun hasSelectedSpecificStore(storeId: String, pageTitle: String)

    fun hasSelectedStore(storeId: String, pageTitle: String)

    fun hasSelectedCategory(categoryId: String, categoryName: String)

    fun openProductDetail(product: Product)
}