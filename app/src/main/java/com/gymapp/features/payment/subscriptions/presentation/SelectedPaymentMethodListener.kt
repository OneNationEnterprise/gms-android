package com.gymapp.features.payment.subscriptions.presentation

import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface

interface SelectedPaymentMethodListener {
    fun hasSelectedPaymentMethod(position: Int, paymentMethod: PaymentMethodInterface, enablePaymentButton: Boolean)
    fun hasSelectedAddNewCreditCard()
}