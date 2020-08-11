package com.gymapp.features.profile.payment.presentation

import com.apollographql.apollo.gym.GetCustomerCardTokensQuery

interface CardsListViewListener {
    fun initRecycleView(cardsList: MutableList<GetCustomerCardTokensQuery.GetCustomerCardToken?>)
    fun openSaveCardActivity()
    fun showLoading()
    fun hideLoading()
    fun showErrorMessage(message: String?)
}