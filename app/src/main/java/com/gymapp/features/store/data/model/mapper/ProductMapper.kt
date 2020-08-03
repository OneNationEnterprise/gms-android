package com.gymapp.features.store.data.model.mapper

import com.apollographql.apollo.gym.StoreHomeQuery
import com.apollographql.apollo.gym.fragment.StoreProductFields
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.Category
import com.gymapp.features.store.data.model.Product

class ProductMapper : BaseDataMapperInterface<StoreProductFields, Product> {

    override fun mapToDto(fields: StoreProductFields): Product {

        return Product(
            id = fields.id,
            name = fields.name,
            status = fields.status,
            description = fields.description,
            listPrice = fields.listPrice,
            salePrice = fields.salePrice,
            warranty = fields.warranty,
            returnPolicy = fields.returnPolicy,
            express = fields.express,
            images = fields.images,
            categoryName = fields.category?.name
        )
    }

    override fun mapToDtoList(input: List<StoreProductFields?>?): List<Product> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }

    fun mapToDtoCustomList(input: List<StoreHomeQuery.BestSeller?>?): List<Product> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!.fragments.storeProductFields)
        }
    }
}