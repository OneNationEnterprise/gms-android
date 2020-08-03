package com.gymapp.features.store.domain.products

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.ProductsFilter
import com.apollographql.apollo.gym.type.StoreHomeInput
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.store.data.StoreRepositoryInterface
import com.gymapp.features.store.data.model.Product
import com.gymapp.features.store.data.model.ProductListData
import com.gymapp.features.store.data.model.StoreHome

class StoreProductsViewModel(private val storeRepositoryInterface: StoreRepositoryInterface) :
    BaseViewModel() {

    val homeData = MutableLiveData<StoreHome?>()
//    val products = MutableLiveData<ProductListData>()
    val products = MutableLiveData<ArrayList<Product>>()

    suspend fun getHomeFeed() {

        homeData.postValue(
            storeRepositoryInterface.getStoreHomepageData(
                StoreHomeInput(
                    6, 6, 6, 6
                )
            )
        )
    }

    suspend fun getProducts(storeId: String?, categoryId: String?) {

        val filter = ProductsFilter(
            brandId = Input.fromNullable(storeId),
            categoryId = Input.fromNullable(categoryId)
        )

        val productsList = storeRepositoryInterface.getProducts(filter)

        products.postValue(productsList)
    }

}
