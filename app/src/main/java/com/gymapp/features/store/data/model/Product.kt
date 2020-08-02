package com.gymapp.features.store.data.model

import com.apollographql.apollo.gym.type.GlobalStatusType

data class Product(
    val id: String,
    val name: String,
    val status: GlobalStatusType,
    val listPrice: Double,
    val salePrice: Double,
    val description: String,
    val warranty: Boolean,
    val returnPolicy: Boolean,
    val express: Boolean,
    val images: List<String>?
)