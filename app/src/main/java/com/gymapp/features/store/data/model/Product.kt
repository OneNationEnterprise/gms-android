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
    val warrantyDescription: String?,
    val returnPolicy: Boolean,
    val returnPolicyDescription: String?,
    val brand: ProductBrand?,
    val express: Boolean,
    val images: List<String>?,
    val categoryName: String?,
    val inventory: List<Inventory?>?
) : HomepageSection {
    override fun getType(): HomepageSectionType {
        return HomepageSectionType.PRODUCT
    }

    override fun getHeaderTitle(): String {
        return "Best Sellers"
    }

    fun getProductActualPrice(): Double {
        return if (salePrice > 0) {
            salePrice
        } else {
            listPrice
        }
    }
}

data class ProductBrand(val id: String, val name: String)

data class Inventory(val gymId: String, val quantity: Int)