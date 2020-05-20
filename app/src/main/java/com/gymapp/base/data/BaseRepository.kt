package com.gymapp.base.data

import androidx.lifecycle.LiveData
import com.apollographql.apollo.gym.CountriesQuery
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.country.CountryMapper
import com.gymapp.main.network.ApiManagerInterface

open class BaseRepository(
    private val apiManager: ApiManagerInterface,
    private val gymDao: GymDao
) : BaseRepositoryInterface {

    private val countryMapper = CountryMapper()

    override fun getCountries(): LiveData<List<Country>>? {
        return gymDao.getCountriesList()
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun saveCountries() {

        val countryQueryResponse = apiManager.getCountriesAsync().await()

        val countriesList = countryQueryResponse.data?.countries?.list

        if (countriesList.isNullOrEmpty()) {
            return
        }

        gymDao.insertCountries(countryMapper.mapToDtoList(countriesList as List<CountriesQuery.List>))
    }

}