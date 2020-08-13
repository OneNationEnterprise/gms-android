package com.cofedistrict.ui.features.payment.adapter.paymentmethods.item

import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.helper.PAYMENT_METHOD_TYPE


class AddNewCardItem: PaymentMethodInterface {
    override fun getType(): Int {
        return PAYMENT_METHOD_TYPE.ADD_CREDIT_CARD.ordinal
    }

    override fun getId(): String {
        return "ADD_CREDIT_CARD"
    }
}