package com.gymapp.features.payment.subscriptions.presentation.adapter.item

import com.apollographql.apollo.gym.GetPaymentMethodsQuery
import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.helper.PAYMENT_METHOD_TYPE


class SavedCardPaymentMethodItem(val paymentMethodDetails: GetPaymentMethodsQuery.GetPaymentMethod) :
    PaymentMethodInterface {
    override fun getType(): Int {
        return PAYMENT_METHOD_TYPE.SAVED_CARD.ordinal
    }

    override fun getId(): String {
        return paymentMethodDetails.fragments.paymentMethodFields.sourceId!!
    }
}