package com.gymapp.base.data

import androidx.lifecycle.LiveData
import com.gymapp.main.data.model.country.Country

interface BaseRepositoryInterface {

    /**
     * return available countries list from db or remote
     */
    fun getCountries(): LiveData<List<Country>>?

    /**
     * save available countries list into db
     */
    suspend fun saveCountries()
}