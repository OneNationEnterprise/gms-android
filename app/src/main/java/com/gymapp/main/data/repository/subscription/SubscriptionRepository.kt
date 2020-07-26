package com.gymapp.main.data.repository.subscription

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.MembershipsQuery
import com.apollographql.apollo.gym.PassesQuery
import com.apollographql.apollo.gym.type.MembershipsFilter
import com.apollographql.apollo.gym.type.PassesFilter
import com.gymapp.main.network.ApiManagerInterface

class SubscriptionRepository(private val apiManagerInterface: ApiManagerInterface) :
    SubscriptionRepositoryInterface {

    override suspend fun getMemberships(input: Input<MembershipsFilter>): List<MembershipsQuery.List?>? {
        val apiResponse = apiManagerInterface.getMembershipAsync(input).await()
        return apiResponse.data?.memberships?.list
    }

    override suspend fun getPasses(input: Input<PassesFilter>): List<PassesQuery.List?>? {
        val apiResponse = apiManagerInterface.getPassesAsync(input).await()
        return apiResponse.data?.passes?.list
    }
}