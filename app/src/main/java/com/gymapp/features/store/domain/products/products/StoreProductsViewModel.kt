package com.gymapp.features.store.domain.products.products

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.transition.TransitionManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.gym.type.ProductsFilter
import com.apollographql.apollo.gym.type.StoreHomeInput
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.store.data.StoreRepositoryInterface
import com.gymapp.features.store.data.model.Product
import com.gymapp.features.store.data.model.ProductListData
import com.gymapp.features.store.data.model.StoreCartProduct
import com.gymapp.features.store.data.model.StoreHome
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface
import org.jetbrains.annotations.NotNull

class StoreProductsViewModel(
    private val storeRepositoryInterface: StoreRepositoryInterface,
    private val configRepositoryInterface: ConfigRepositoryInterface
) :
    BaseViewModel() {

    val homeData = MutableLiveData<StoreHome?>()

    val products = MutableLiveData<ArrayList<Product>>()
    val showItemsInCart = MutableLiveData<Pair<Int, Double>>()
    val hideCart = MutableLiveData<Boolean>()

    suspend fun getHomeFeed() {

        val country = configRepositoryInterface.getCountries().first() {
            it.isoCode == "AE"
        }

        try {
            homeData.postValue(
                storeRepositoryInterface.getStoreHomepageData(
                    StoreHomeInput(
                        country.countryId
                    )
                )
            )
        } catch (e: ApolloHttpException) {
        }
    }

    suspend fun getProducts(storeId: String?, categoryId: String?) {

        val filter = ProductsFilter(
            brandId = Input.fromNullable(storeId),
            categoryId = Input.fromNullable(categoryId)
        )

        try {
            val productsList = storeRepositoryInterface.getProducts(filter)
            products.postValue(productsList)
        } catch (e: ApolloHttpException) {
        }
    }

    fun addProductToCart(product: Product) {
        storeRepositoryInterface.addProductToStoreCart(StoreCartProduct(product))
        fetchData()
    }

    fun fetchData() {
        val products = storeRepositoryInterface.getStoreCartProducts()
        if (products.size > 0) {
            showItemsInCart.value =
                Pair(products.size, storeRepositoryInterface.getStoreProductsValue())
        } else {
            hideCart.value = true
        }
    }

    fun invalidateDataCache() {
        storeRepositoryInterface.invalidateStoreCartProducts()
    }


}
