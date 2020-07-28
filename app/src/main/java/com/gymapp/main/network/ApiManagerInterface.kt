package com.gymapp.main.network

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.gym.*
import com.apollographql.apollo.gym.type.*
import kotlinx.coroutines.Deferred

interface ApiManagerInterface {

    suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>>

    suspend fun registerUserAsync(input: RegisterCustomerInput): Deferred<Response<RegisterUserMutation.Data>>

    suspend fun getUserDetailsByEmailAsync(email: String): Deferred<Response<CustomerByEmailQuery.Data>>

    suspend fun getGymsInRadiusAsync(input: GymsInRadiusFilter): Deferred<Response<GymsInRadiusQuery.Data>>

    suspend fun getGymDetailAsync(id: String): Deferred<Response<GymQuery.Data>>

    suspend fun getGymCategoriesAsync(input: Input<GymClassCategoryFilter>): Deferred<Response<GymClassCategoriesQuery.Data>>

    suspend fun getMembershipAsync(input: Input<MembershipsFilter>): Deferred<Response<MembershipsQuery.Data>>

    suspend fun getPassesAsync(input: Input<PassesFilter>): Deferred<Response<PassesQuery.Data>>

    suspend fun getClassesAsync(input: Input<GymClassesFilter>): Deferred<Response<ClassesQuery.Data>>
}