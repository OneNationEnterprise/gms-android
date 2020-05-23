package com.gymapp.main.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.CustomerByEmailQuery
import com.apollographql.apollo.gym.RegisterUserMutation
import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.gymapp.base.data.BaseApiManager
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient

class ApiManagerImpl(okHttpClient: OkHttpClient) : BaseApiManager(okHttpClient),
    ApiManagerInterface {

    override suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>> {
        return graphQlNoAuthClient.query(CountriesQuery()).toDeferred()
    }

    override suspend fun registerUserAsync(input: RegisterCustomerInput): Deferred<Response<RegisterUserMutation.Data>> {
        return graphQlNoAuthClient.mutate(RegisterUserMutation(input)).toDeferred()
    }

    override suspend fun getUserDetailsByEmailAsync(email: String): Deferred<Response<CustomerByEmailQuery.Data>> {
        return graphQlClient.query(CustomerByEmailQuery(email)).toDeferred()
    }
}