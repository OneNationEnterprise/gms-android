package com.gymapp.features.store.data.model.mapper

import com.apollographql.apollo.gym.StoreHomeQuery
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.Category
import com.gymapp.features.store.data.model.Product

class ProductMapper : BaseDataMapperInterface<StoreHomeQuery.BestSeller, Product> {

    override fun mapToDto(input: StoreHomeQuery.BestSeller): Product {
        val fields = input.fragments.storeProductFields

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
            images = fields.images
        )
    }

    override fun mapToDtoList(input: List<StoreHomeQuery.BestSeller?>?): List<Product> {

        if (input.isNullOrEmpty()) return emptyList()

        return input.map {
            mapToDto(it!!)
        }
    }
}