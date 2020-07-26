package com.gymapp.features.subscriptions.presentation.adapter

import com.gymapp.main.data.model.subscription.Subscription

interface SubscriptionSelectedListener {

    fun onSubscriptionSelected(subscription: Subscription)
}