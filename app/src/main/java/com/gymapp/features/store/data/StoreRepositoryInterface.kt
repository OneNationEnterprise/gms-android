package com.gymapp.features.store.data

import com.apollographql.apollo.gym.type.StoreHomeInput
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.features.store.data.model.StoreHome

interface StoreRepositoryInterface : BaseRepositoryInterface {

    suspend fun getStoreHomepageData(input: StoreHomeInput?) : StoreHome?
}