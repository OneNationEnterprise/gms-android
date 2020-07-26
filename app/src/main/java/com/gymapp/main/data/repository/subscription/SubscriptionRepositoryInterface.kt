package com.gymapp.main.data.repository.subscription

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.MembershipsQuery
import com.apollographql.apollo.gym.PassesQuery
import com.apollographql.apollo.gym.type.MembershipsFilter
import com.apollographql.apollo.gym.type.PassesFilter
import com.gymapp.base.data.BaseRepositoryInterface

interface SubscriptionRepositoryInterface : BaseRepositoryInterface {

    suspend fun getMemberships(input: Input<MembershipsFilter>): List<MembershipsQuery.List?>?

    suspend fun getPasses(input: Input<PassesFilter>): List<PassesQuery.List?>?
}