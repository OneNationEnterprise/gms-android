package com.gymapp.main.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.fragment.CountryFields
import com.gymapp.BuildConfig
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient

class ApiManagerImpl(var okHttpClient: OkHttpClient) : ApiManagerInterface {

    private val graphQlClient: ApolloClient by lazy {
        ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl("${BuildConfig.BASE_SERVER_URL}")
            .build()
    }

    override suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>> {
        return graphQlClient.query(CountriesQuery()).toDeferred()
    }

}