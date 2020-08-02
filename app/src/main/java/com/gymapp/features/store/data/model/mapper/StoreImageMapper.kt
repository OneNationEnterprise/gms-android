package com.gymapp.features.store.data.model.mapper

import com.apollographql.apollo.gym.StoreHomeQuery
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.StoreImage

class StoreImageMapper : BaseDataMapperInterface<StoreHomeQuery.StoreImage, StoreImage> {

    override fun mapToDto(input: StoreHomeQuery.StoreImage): StoreImage {
        return StoreImage(
            id = input.id,
            image = input.image
        )
    }

    override fun mapToDtoList(input: List<StoreHomeQuery.StoreImage?>?): List<StoreImage> {

        if(input.isNullOrEmpty()) return emptyList()

        return input.map {
            mapToDto(it!!)
        }
    }
}