package com.gymapp.features.store.data.model

import com.apollographql.apollo.gym.type.GlobalStatusType
import com.gymapp.features.store.data.HomepageSection
import com.gymapp.helper.HomepageSectionType

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
    val images: List<String>?,
    val categoryName: String?
) : HomepageSection {
    override fun getType(): HomepageSectionType {
        return HomepageSectionType.PRODUCT
    }

    override fun getHeaderTitle(): String {
        return "Best Sellers"
    }
}