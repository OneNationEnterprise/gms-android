package com.gymapp.features.store.domain.cart

import androidx.lifecycle.MutableLiveData
import com.gymapp.features.store.presentation.cart.adapter.item.BrandItem
import com.gymapp.features.store.presentation.cart.adapter.item.ProductItem
import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.gymapp.features.store.presentation.cart.adapter.item.SubtotalItem
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.store.data.StoreRepositoryInterface
import com.gymapp.features.store.presentation.cart.adapter.ChangeListener

class StoreCartViewModel(val storeRepositoryInterface: StoreRepositoryInterface) : BaseViewModel(),
    ChangeListener {

    var cartItemList = MutableLiveData<MutableList<StoreProductItem>>()
    var closeActivity = MutableLiveData<Boolean>()
    var openSelectAddress = MutableLiveData<MutableList<StoreProductItem>>()


    fun openSelectAddress() {
        openSelectAddress.value = cartItemList.value
    }

    fun fetchData() {
        val products = storeRepositoryInterface.getStoreCartProducts()

        val brandIds: MutableList<String> = ArrayList()

        val recycleViewList: MutableList<StoreProductItem> = ArrayList()

        var totalBrandProductsValue: Double = 0.00

        for (i in 0 until products.size) {
            if (!brandIds.contains(products[i].product.brand!!.id)) {

                if (brandIds.isNotEmpty()) {
                    recycleViewList.add(SubtotalItem(totalBrandProductsValue))
                    totalBrandProductsValue = 0.00
                }

                brandIds.add(products[i].product.brand!!.id)

                recycleViewList.add(BrandItem(products[i].product.brand!!.name))
            }

            totalBrandProductsValue += products[i].product.getProductActualPrice().toString()
                .toDouble() * products[i].quantity

            recycleViewList.add(ProductItem(products[i]))

            if (i == products.size - 1) {
                recycleViewList.add(SubtotalItem(totalBrandProductsValue))
                totalBrandProductsValue = 0.00
            }
        }

        cartItemList.value = recycleViewList
    }


    override fun onAddClick(productId: String) {
        val products = storeRepositoryInterface.getStoreCartProducts()

        val product = products.find {
            it.product.id == productId
        } ?: return

        product.quantity += 1

        fetchData()
    }

    override fun onMinusClick(productId: String) {
        val products = storeRepositoryInterface.getStoreCartProducts()

        val product = products.find {
            it.product.id == productId
        } ?: return

        product.quantity -= 1

        if (product.quantity <= 0) {
            storeRepositoryInterface.removeProductFromStoreCart(product)

            if (storeRepositoryInterface.getStoreCartProducts().size <= 0) {
                closeActivity.value = true
            }
        }

        fetchData()
    }
}