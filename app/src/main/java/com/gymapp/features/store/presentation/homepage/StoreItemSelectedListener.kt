package com.gymapp.features.store.presentation.homepage

interface StoreItemSelectedListener {

    fun hasSelectedSeeAllStores()

    fun hasSelectedSpecificStore(storeId: String, pageTitle: String)

    fun hasSelectedStore(storeId: String, pageTitle: String)

    fun hasSelectedCategory(categoryId: String, categoryName: String)
}