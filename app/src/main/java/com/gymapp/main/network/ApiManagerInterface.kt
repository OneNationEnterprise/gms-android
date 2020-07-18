package com.gymapp.main.network

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.gym.*
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.apollographql.apollo.gym.type.RegisterCustomerInput
import kotlinx.coroutines.Deferred

interface ApiManagerInterface {

    suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>>

    suspend fun registerUserAsync(input: RegisterCustomerInput): Deferred<Response<RegisterUserMutation.Data>>

    suspend fun getUserDetailsByEmailAsync(email: String): Deferred<Response<CustomerByEmailQuery.Data>>

    suspend fun getGymsInRadiusAsync(input: GymsInRadiusFilter): Deferred<Response<GymsInRadiusQuery.Data>>

    suspend fun getGymDetailAsync(id: String): Deferred<Response<GymQuery.Data>>

}