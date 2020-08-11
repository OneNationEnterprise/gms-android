package com.gymapp.features.profile.payment.presentation

import com.apollographql.apollo.gym.CustomerCardTokenSaveMutation

interface CardViewInterface {

    fun setInvalidCardNumber()
    fun setInvalidDate()
    fun setInvalidCVV()
    fun setInvalidNameOnCard()
    fun showLoading()
    fun hideLoading()
    fun showErrorMessage(message: String?)
    fun onCardSavedSuccess(data: CustomerCardTokenSaveMutation.CustomerCardToken)
    fun initCheckoutApi(publicKey: String)
}