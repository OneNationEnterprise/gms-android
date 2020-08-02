package com.gymapp.features.store.data

import com.apollographql.apollo.gym.type.StoreHomeInput
import com.gymapp.base.data.BaseRepository
import com.gymapp.features.store.data.model.StoreHome
import com.gymapp.features.store.data.model.StoreHomeMapper
import com.gymapp.main.network.ApiManagerInterface

class StoreRepository(private val apiManagerInterface: ApiManagerInterface) :
    BaseRepository(apiManagerInterface), StoreRepositoryInterface {

    private val storeHomeMapper = StoreHomeMapper()

    private var storeHome: StoreHome? = null

    override suspend fun getStoreHomepageData(input: StoreHomeInput?): StoreHome? {

        if (storeHome != null) {
            return storeHome
        }

        val apiResponse = apiManagerInterface.getStoreHomeAsync(input).await()

        if (apiResponse.data?.storeHome != null) {
            val storeHomeData = apiResponse.data!!.storeHome

            return storeHomeMapper.mapToDto(storeHomeData)
        }

        return null
    }


}