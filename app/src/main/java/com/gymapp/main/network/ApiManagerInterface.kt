package com.gymapp.main.network

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.gym.CountriesQuery
import kotlinx.coroutines.Deferred

interface ApiManagerInterface {

    suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>>

}