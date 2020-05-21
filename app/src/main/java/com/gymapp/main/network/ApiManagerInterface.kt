package com.gymapp.main.network

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.RegisterUserMutation
import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.gymapp.main.data.model.user.User
import kotlinx.coroutines.Deferred

interface ApiManagerInterface {

    suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>>

    suspend fun registerUserAsync(input: RegisterCustomerInput): Deferred<Response<RegisterUserMutation.Data>>

}