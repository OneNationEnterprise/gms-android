package com.gymapp.main.data.repository.config

import com.gymapp.main.data.model.country.Country

interface ConfigRepositoryInterface {
    /**
     * returns available countries list from db or remote
     */
    fun getCountries(): ArrayList<Country>

    /**
     * save available countries list into db
     */
    suspend fun saveCountries()

}