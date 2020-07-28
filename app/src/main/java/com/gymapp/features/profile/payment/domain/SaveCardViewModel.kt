package com.gymapp.features.profile.payment.domain

import com.android.volley.VolleyError
import com.checkout.android_sdk.CheckoutAPIClient
import com.checkout.android_sdk.Request.CardTokenisationRequest
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Utils.CardUtils
import com.gymapp.base.domain.BaseViewModel

class SaveCardViewModel : BaseViewModel(), CheckoutAPIClient.OnTokenGenerated {

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

    }

    override fun onError(error: CardTokenisationFail) { // your error
//     showErrorMessage(error.message)
    }

    override fun onNetworkError(error: VolleyError) { // your network error
//      showErrorMessage(error.message)
    }

}