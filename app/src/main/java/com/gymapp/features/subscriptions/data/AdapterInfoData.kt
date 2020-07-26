package com.gymapp.features.subscriptions.data

import androidx.lifecycle.MutableLiveData
import com.gymapp.features.subscriptions.presentation.adapter.SubscriptionSelectedListener
import com.gymapp.main.data.model.subscription.Subscription

data class AdapterInfoData(
    val subscriptionList: List<Subscription>,
    val selectedItemId: String,
    val subscriptionSelectedListener: SubscriptionSelectedListener
)