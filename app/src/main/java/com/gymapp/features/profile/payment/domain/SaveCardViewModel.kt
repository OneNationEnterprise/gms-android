package com.gymapp.features.profile.payment.domain

import com.android.volley.VolleyError
import com.apollographql.apollo.gym.type.CustomerCardTokenInput
import com.checkout.android_sdk.CheckoutAPIClient
import com.checkout.android_sdk.Request.CardTokenisationRequest
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.profile.payment.presentation.CardViewInterface
import com.gymapp.main.network.ApiManagerInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SaveCardViewModel(val apiManagerInterface: ApiManagerInterface) : BaseViewModel(),
    CheckoutAPIClient.OnTokenGenerated {

    lateinit var viewInterface: CardViewInterface

    fun tokenizeCard(
        mCheckoutAPIClient: CheckoutAPIClient,
        cardNumberFormatted: String,
        expiryDate: String,
        cvv: String
    ) {

        val cardNumber = cardNumberFormatted.replace(" ", "")

        // Pass the payload and generate the token
        mCheckoutAPIClient.generateToken(
            CardTokenisationRequest(
                cardNumber,
                null,
                expiryDate.substring(0, 2),
                expiryDate.substring(3, 5),
                cvv,
                null
            )
        )
    }

    override fun onTokenGenerated(response: CardTokenisationResponse?) {
        if (response?.id.isNullOrEmpty()) {
            viewInterface?.showErrorMessage("Null token response from Checkout")
            return
        }
        viewInterface.showLoading()

        GlobalScope.launch {
            saveCard(response)
        }

    }

    suspend fun saveCard(response: CardTokenisationResponse?) {
        val apiresponse =
            apiManagerInterface.customerCardTokenSaveAsync(CustomerCardTokenInput(token = response!!.id))
                .await()

        if (apiresponse.data?.customerCardTokenSave?.customerCardToken == null) {
            viewInterface.showErrorMessage(apiresponse.data?.customerCardTokenSave?.error?.rawValue)
            return
        }

        viewInterface.onCardSavedSuccess(apiresponse.data?.customerCardTokenSave?.customerCardToken!!)
    }

    override fun onError(error: CardTokenisationFail) { // your error
        viewInterface.showErrorMessage(error.message)
    }

    override fun onNetworkError(error: VolleyError) { // your network error
        viewInterface.showErrorMessage(error.message)
    }


    suspend fun initCheckoutApi() {
        val checkoutConfig = apiManagerInterface.getCheckoutComConfigAsync().await()

        if (!checkoutConfig.errors.isNullOrEmpty() || checkoutConfig.data?.getCheckoutComConfig == null) {
            viewInterface.showErrorMessage("Invalid Checkout config data. Please try again later.")
            return
        }

        viewInterface.initCheckoutApi(checkoutConfig.data?.getCheckoutComConfig?.publicKey!!)
    }
}