package com.gymapp.features.store.domain.products.brandList

import androidx.lifecycle.MutableLiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.store.data.StoreRepositoryInterface
import com.gymapp.features.store.data.model.Store

class StoreBrandListViewModel(private val storeRepositoryInterface: StoreRepositoryInterface) :
    BaseViewModel() {

    private val brandsList: MutableList<Store> = ArrayList()
    val filteredBrandsList = MutableLiveData<MutableList<Store>>()

    fun filterBrandsList(searchText: String?) {

        if (searchText == null || searchText.isEmpty()) {
            filteredBrandsList.postValue(brandsList)
        }

        val filteredList = brandsList.filter {
            it.name.toLowerCase().contains(searchText!!.toLowerCase())
        }

        filteredBrandsList.postValue(filteredList.toMutableList())
    }

    fun fetchData() {

        val stores = storeRepositoryInterface.getCachedStores()

        if (stores.isNullOrEmpty()) return

        brandsList.clear()

        brandsList.addAll(stores)

        filteredBrandsList.postValue(brandsList)
    }

}