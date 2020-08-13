package com.gymapp.features.payment.subscriptions.presentation.adapter.item

import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.helper.PAYMENT_METHOD_TYPE

class ChoosePaymentMethodItem() : PaymentMethodInterface {
    override fun getType(): Int {
        return PAYMENT_METHOD_TYPE.CHOOSE_PAYMENT_METHOD.ordinal
    }

    override fun getId(): String {
        return "Choose.payment.id"
    }

}