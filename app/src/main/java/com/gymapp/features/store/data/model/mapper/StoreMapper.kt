package com.gymapp.features.store.data.model.mapper

import com.apollographql.apollo.gym.StoreHomeQuery
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.Store

class StoreMapper : BaseDataMapperInterface<StoreHomeQuery.Store, Store> {

    override fun mapToDto(input: StoreHomeQuery.Store): Store {
        return Store(
            id = input.id,
            name = input.name,
            status = input.status,
            logo = input.logo
        )
    }

    override fun mapToDtoList(input: List<StoreHomeQuery.Store?>?): List<Store> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }
}