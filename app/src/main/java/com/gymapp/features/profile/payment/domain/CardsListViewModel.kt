package com.gymapp.features.profile.payment.domain

import com.apollographql.apollo.gym.GetCustomerCardTokensQuery
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.profile.payment.presentation.CardsListViewListener
import com.gymapp.main.network.ApiManagerInterface

class CardsListViewModel(val apiManagerInterface: ApiManagerInterface) : BaseViewModel() {

    lateinit var cardsListViewListener: CardsListViewListener

    private var cardsList: MutableList<GetCustomerCardTokensQuery.GetCustomerCardToken?> =
        ArrayList()

    suspend fun onDeleteCardClicked(cardId: String) {
        cardsListViewListener.showLoading()

        cardsListViewListener.showLoading()

        val apiResponse = apiManagerInterface.customerCardTokenDeleteAsync(cardId).await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()) {
            cardsListViewListener.showErrorMessage(apiResponse.errors()!![0].message())
            return
        }

        if (apiResponse.data != null) {

            cardsList = cardsList.toMutableList()

            cardsList.removeIf {
                it?.id == cardId
            }
            cardsListViewListener.initRecycleView(cardsList)
        }
    }


    suspend fun fetchData() {

        cardsListViewListener.showLoading()

        val apiResponse = apiManagerInterface.getCheckoutComSavedCardTokensAsync().await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()) {
            cardsListViewListener.showErrorMessage(apiResponse.errors()!![0].message())
            return
        }

        if (apiResponse.data != null) {
            cardsList = apiResponse.data!!.getCustomerCardTokens.toMutableList()
        }

        cardsListViewListener.initRecycleView(cardsList)
    }
}
