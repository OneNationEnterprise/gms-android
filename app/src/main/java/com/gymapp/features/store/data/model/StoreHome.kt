package com.gymapp.features.store.data.model

import com.apollographql.apollo.gym.StoreHomeQuery
import com.apollographql.apollo.gym.type.GlobalStatusType
import com.gymapp.features.store.data.HomepageSection
import com.gymapp.helper.HomepageSectionType

data class StoreHome(
    val images: List<StoreImage>,
    val stores: List<Store>,
    val categories: List<Category>,
    val bestSeller: List<Product>
)


data class StoreImage(val id: String, val image: String) : HomepageSection {
    override fun getType(): HomepageSectionType {
        return HomepageSectionType.IMAGE
    }

    override fun getHeaderTitle(): String {
        return ""
    }
}

data class Store(val id: String, val name: String, val status: GlobalStatusType, val logo: String) :
    HomepageSection {
    override fun getType(): HomepageSectionType {
        return HomepageSectionType.STORE
    }

    override fun getHeaderTitle(): String {
        return "Stores"
    }
}

data class Category(
    val id: String,
    val name: String,
    val status: GlobalStatusType,
    val image: String?
) : HomepageSection {
    override fun getType(): HomepageSectionType {
        return HomepageSectionType.CATEGORY
    }

    override fun getHeaderTitle(): String {
        return "Categories"
    }
}