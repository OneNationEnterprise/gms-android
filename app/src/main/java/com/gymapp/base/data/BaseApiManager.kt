package com.gymapp.base.data

import com.apollographql.apollo.ApolloClient
import com.gymapp.BuildConfig
import okhttp3.OkHttpClient

//open class BaseApiManager(private var okHttpClient: OkHttpClient) {
//
//    val graphQl: ApolloClient by lazy {
//        ApolloClient.builder()
//            .okHttpClient(okHttpClient)
//            .serverUrl("${BuildConfig.BASE_SERVER_URL}")
//            .build()
//    }
//
//}