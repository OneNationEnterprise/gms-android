package com.gymapp.base.data

import com.apollographql.apollo.ApolloClient
import com.gymapp.BuildConfig
import com.gymapp.main.network.interceptors.AuthHeaderTokenInterceptor
import okhttp3.OkHttpClient

open class BaseApiManager(private var okHttpClient: OkHttpClient) {

    val graphQlNoAuthClient: ApolloClient by lazy {

        val b = okHttpClient.newBuilder()
        b.interceptors().remove(
            b.interceptors().find { it.javaClass == AuthHeaderTokenInterceptor::class.java })
        val noAuthTokenHttpClient = b.build()

        ApolloClient.builder()
            .okHttpClient(noAuthTokenHttpClient)
            .serverUrl("${BuildConfig.BASE_SERVER_URL}")
            .build()
    }

    val graphQlClient: ApolloClient by lazy {
        ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl("${BuildConfig.BASE_SERVER_URL}")
            .build()
    }


}