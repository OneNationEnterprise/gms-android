package com.gymapp.features.subscriptions.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.*
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.subscriptions.data.AdapterInfoData
import com.gymapp.features.subscriptions.presentation.adapter.SubscriptionSelectedListener
import com.gymapp.helper.SubscriptionType
import com.gymapp.main.data.model.subscription.Subscription
import com.gymapp.main.data.repository.subscription.SubscriptionRepositoryInterface

class SubscriptionViewModel(val repository: SubscriptionRepositoryInterface) : BaseViewModel(),
    SubscriptionSelectedListener {

    val subscriptionAdapterData = MutableLiveData<AdapterInfoData>()

    val selectedSubscription = MutableLiveData<Subscription>()
    private lateinit var gymId: String
    private lateinit var subscriptionList: List<Subscription>

    suspend fun fetchData(gymId: String?, subscriptionTypeType: SubscriptionType?) {

        if (gymId.isNullOrEmpty() || subscriptionTypeType == null) {
            return
        }

        this.gymId = gymId

        when (subscriptionTypeType) {
            SubscriptionType.MEMBERSHIP -> {

                subscriptionList = getMembershipData(gymId)

                subscriptionAdapterData.postValue(
                    AdapterInfoData(
                        subscriptionList,
                        "-1",
                        this
                    )
                )
            }
            SubscriptionType.PASS -> {
                subscriptionList = getPassData(gymId)

                subscriptionAdapterData.postValue(
                    AdapterInfoData(
                        subscriptionList,
                        "-1",
                        this
                    )
                )
            }
        }
    }

    private suspend fun getMembershipData(gymId: String): List<Subscription> {

        val memberships = repository.getMemberships(
            Input.fromNullable(
                MembershipsFilter(
                    status = Input.fromNullable(GlobalStatusType.ACTIVE),
                    accessType = Input.fromNullable(MembershipAccessType.BRANCH),
                    referenceTypeId = Input.fromNullable(gymId)
                )
            )
        )

        if (memberships.isNullOrEmpty()) return ArrayList()

        return memberships.map {
            if (it == null) return ArrayList()
            Subscription(
                id = it.id,
                amount = it.amount.toString(),
                amountLabel = it.amountLabel,
                name = it.name,
                description = it.description,
                colorCode = it.colorCode,
                image = it.image,
                type = SubscriptionType.MEMBERSHIP
            )
        }
    }

    private suspend fun getPassData(gymId: String): List<Subscription> {
        val passes = repository.getPasses(
            Input.fromNullable(
                PassesFilter(
                    status = Input.fromNullable(GlobalStatusType.ACTIVE),
                    accessType = Input.fromNullable(PassAccessType.BRANCH),
                    referenceTypeId = Input.fromNullable(gymId)
                )
            )
        )

        if (passes.isNullOrEmpty()) return ArrayList()

        return passes.map {
            if (it == null) return ArrayList()
            Subscription(
                id = it.id,
                amount = it.amount.toString(),
                amountLabel = "todo",
                name = it.name,
                description = it.description,
                colorCode = it.colorCode,
                image = it.image,
                type = SubscriptionType.PASS
            )
        }
    }

    override fun onSubscriptionSelected(subscription: Subscription) {

        selectedSubscription.value = subscription
    }


}