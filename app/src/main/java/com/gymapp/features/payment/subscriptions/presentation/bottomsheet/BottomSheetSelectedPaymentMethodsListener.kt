package com.gymapp.features.payment.subscriptions.presentation.bottomsheet

import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface

interface BottomSheetSelectedPaymentMethodsListener {

    fun hasSelectedPaymentMethod(selectedPM: PaymentMethodInterface)
    fun hasSelectedAddCreditCard()
}