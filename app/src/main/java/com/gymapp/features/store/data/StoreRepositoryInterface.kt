package com.gymapp.features.store.data

import com.apollographql.apollo.gym.type.ProductsFilter
import com.apollographql.apollo.gym.type.StoreHomeInput
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.features.store.data.model.Product
import com.gymapp.features.store.data.model.Store
import com.gymapp.features.store.data.model.StoreCartProduct
import com.gymapp.features.store.data.model.StoreHome

interface StoreRepositoryInterface : BaseRepositoryInterface {

    suspend fun getStoreHomepageData(input: StoreHomeInput?): StoreHome?
    fun getCachedStores(): List<Store>?
    suspend fun getProducts(input: ProductsFilter): ArrayList<Product>
    fun getProductDetail(productId: String): Product
    fun getStoreCartProducts(): MutableList<StoreCartProduct>
    fun getStoreProductsValue(): Double
    fun invalidateStoreCartProducts()
    fun addProductToStoreCart(product: StoreCartProduct)
    fun removeProductFromStoreCart(product: StoreCartProduct)
}