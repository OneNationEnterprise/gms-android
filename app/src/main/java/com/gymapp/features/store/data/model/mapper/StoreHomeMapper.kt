package com.gymapp.features.store.data.model.mapper

import com.apollographql.apollo.gym.StoreHomeQuery
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.StoreHome

class StoreHomeMapper : BaseDataMapperInterface<StoreHomeQuery.StoreHome, StoreHome> {

    private val storeImageMapper = StoreImageMapper()
    private val storeMapper = StoreMapper()
    private val categoriesMapper = CategoryMapper()
    private val productMapper = ProductMapper()

    override fun mapToDto(input: StoreHomeQuery.StoreHome): StoreHome {
        return StoreHome(
            images = storeImageMapper.mapToDtoList(input.storeImages),
            stores = storeMapper.mapToDtoList(input.stores),
            categories = categoriesMapper.mapToDtoList(input.categories),
            bestSeller = productMapper.mapToDtoCustomList(input.bestSellers)
        )
    }

    override fun mapToDtoList(input: List<StoreHomeQuery.StoreHome?>?): List<StoreHome> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }
}