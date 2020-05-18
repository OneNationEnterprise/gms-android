package com.gymapp.base.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.gym.CountriesQuery
import com.gymapp.BuildConfig
import com.gymapp.main.network.ApiManagerInterface
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient

open class BaseApiManager(private var okHttpClient: OkHttpClient)  {

    val graphQlClient: ApolloClient by lazy {
        ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl("${BuildConfig.BASE_SERVER_URL}")
            .build()
    }



}