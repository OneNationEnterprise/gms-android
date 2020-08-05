package com.gymapp.features.store.data

import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.gym.type.PaginatorInput
import com.apollographql.apollo.gym.type.ProductsFilter
import com.apollographql.apollo.gym.type.StoreHomeInput
import com.gymapp.base.data.BaseRepository
import com.gymapp.features.store.data.model.Product
import com.gymapp.features.store.data.model.Store
import com.gymapp.features.store.data.model.StoreCartProduct
import com.gymapp.features.store.data.model.StoreHome
import com.gymapp.features.store.data.model.mapper.ProductMapper
import com.gymapp.features.store.data.model.mapper.StoreHomeMapper
import com.gymapp.main.network.ApiManagerInterface

class StoreRepository(private val apiManagerInterface: ApiManagerInterface) :
    BaseRepository(apiManagerInterface), StoreRepositoryInterface {

    private val storeHomeMapper = StoreHomeMapper()
    private val productsMapper = ProductMapper()

    private var storeHome: StoreHome? = null
    private var productsList = ArrayList<Product>()

    private var storeCartProductsList: MutableList<StoreCartProduct> = ArrayList()

    override suspend fun getStoreHomepageData(input: StoreHomeInput?): StoreHome? {

        if (storeHome != null) {
            return storeHome
        }

        val apiResponse = apiManagerInterface.getStoreHomeAsync(input).await()

        if (apiResponse.data?.storeHome != null) {
            val storeHomeData = apiResponse.data!!.storeHome

            storeHome = storeHomeMapper.mapToDto(storeHomeData)
            return storeHome
        }

        return null
    }

    override fun getCachedStores(): List<Store>? {
        return storeHome?.stores
    }

    override suspend fun getProducts(input: ProductsFilter): ArrayList<Product> {

        try {
            val apiResponse =
                apiManagerInterface.getProductsAsync(input, PaginatorInput(0, 100)).await()

            if (apiResponse.data?.products != null) {
                val products = apiResponse.data!!.products.list

                productsList.clear()

                for (product in products!!) {
                    if (product?.fragments?.storeProductFields != null) {
                        productsList.add(productsMapper.mapToDto(product.fragments.storeProductFields))
                    }
                }

                return productsList
            }

        } catch (e: ApolloHttpException) {
        }

        return ArrayList()
    }

    override fun getProductDetail(productId: String): Product {
        return productsList.first {
            it.id == productId
        }
    }


    override fun getStoreCartProducts(): MutableList<StoreCartProduct> {
        return storeCartProductsList
    }

    override fun getStoreProductsValue(): Double {
        var value: Double = 0.00
        for (product in storeCartProductsList) {
            value += (product.product.getProductActualPrice() * product.quantity)
        }

        return value
    }

    override fun invalidateStoreCartProducts() {
        storeCartProductsList = ArrayList()
    }

    override fun addProductToStoreCart(product: StoreCartProduct) {

        try {
            val prod = storeCartProductsList.first {
                it.product.id == product.product.id
            }

            prod.quantity += 1

        } catch (e: NoSuchElementException) {
            storeCartProductsList.add(product)
        }

    }

    override fun removeProductFromStoreCart(product: StoreCartProduct) {
        storeCartProductsList.remove(product)
    }

}