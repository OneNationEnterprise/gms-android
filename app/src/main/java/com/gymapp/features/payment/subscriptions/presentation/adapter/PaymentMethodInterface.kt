package com.gymapp.features.payment.subscriptions.presentation.adapter

interface PaymentMethodInterface {
    fun  getType(): Int
    /**
     * Used for calculating last selected payment method;
     * Because Payment methods from server response don't have a unique id (saved cards have the same id -> CARD)
     *  returns ${id}${source.id}
     */
    fun getId() : String

}