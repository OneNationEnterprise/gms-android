package com.gymapp.main.data.model.subscription

import com.gymapp.helper.SubscriptionType

data class Subscription (
    val id: String,
    val name: String,
    val description: String?,
    val amount: String,
    val amountLabel: String,
    val colorCode: String?,
    val image: String?,
    val type: SubscriptionType
)