package com.gymapp.features.payment.subscriptions.presentation.adapter

import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface

interface AvailablePaymentMethodsListener {

    fun hasSelectedPaymentMethod(paymentMethod: PaymentMethodInterface)
    fun hasSelectedAddNewCreditCard()
    fun notifyDataSetChanged(paymentMethod: PaymentMethodInterface)
}