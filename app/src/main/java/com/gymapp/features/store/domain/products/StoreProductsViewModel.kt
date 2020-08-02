package com.gymapp.features.store.domain.products

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.StoreHomeInput
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.store.data.StoreRepositoryInterface
import com.gymapp.features.store.data.model.StoreHome

class StoreProductsViewModel(private val storeRepositoryInterface: StoreRepositoryInterface) :
    BaseViewModel() {

    val homeData = MutableLiveData<StoreHome>()

    suspend fun getHomeFeed() {
        val homeData = storeRepositoryInterface.getStoreHomepageData(null)
    }

}
