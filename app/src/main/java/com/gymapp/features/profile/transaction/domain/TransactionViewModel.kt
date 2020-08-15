package com.gymapp.features.profile.transaction.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.TransactionsQuery
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.network.ApiManagerInterface

class TransactionViewModel(private val apiManagerInterface: ApiManagerInterface) : BaseViewModel() {

    val error = MutableLiveData<String>()
    val list = MutableLiveData<List<TransactionsQuery.List?>>()

    suspend fun getData() {
        val apiResponse = apiManagerInterface.getTransactionsAsync().await()

        if (apiResponse.errors != null && apiResponse.errors?.isNotEmpty()!!) {
            error.postValue(apiResponse.errors!![0].message)
            return
        }

        if (apiResponse.data == null) {
            error.postValue("Error getting data")
            return
        }

        list.postValue(apiResponse.data?.transactions?.list)
    }
}