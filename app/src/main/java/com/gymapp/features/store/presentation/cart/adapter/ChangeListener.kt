package com.gymapp.features.store.presentation.cart.adapter

interface ChangeListener {

    fun onAddClick(productId: String)

    fun onMinusClick(productId: String)
}