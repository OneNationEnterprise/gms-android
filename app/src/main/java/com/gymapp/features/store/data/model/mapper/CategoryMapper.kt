package com.gymapp.features.store.data.model.mapper

import com.apollographql.apollo.gym.StoreHomeQuery
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.Category

class CategoryMapper : BaseDataMapperInterface<StoreHomeQuery.Category, Category> {

    override fun mapToDto(input: StoreHomeQuery.Category): Category {
        return Category(
            id = input.id,
            name = input.name,
            status = input.status,
            image = input.image
        )
    }

    override fun mapToDtoList(input: List<StoreHomeQuery.Category?>?): List<Category> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }
}