package com.gymapp.features.store.data.model

import com.apollographql.apollo.gym.StoreHomeQuery
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.mapper.CategoryMapper
import com.gymapp.features.store.data.model.mapper.ProductMapper
import com.gymapp.features.store.data.model.mapper.StoreImageMapper
import com.gymapp.features.store.data.model.mapper.StoreMapper

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
            bestSeller = productMapper.mapToDtoList(input.bestSellers)
        )
    }

    override fun mapToDtoList(input: List<StoreHomeQuery.StoreHome?>?): List<StoreHome> {

        if (input.isNullOrEmpty()) return emptyList()

        return input.map {
            mapToDto(it!!)
        }
    }
}