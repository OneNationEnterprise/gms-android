package com.gymapp.base.data

import androidx.lifecycle.LiveData
import com.apollographql.apollo.gym.CountriesQuery
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.country.CountryMapper
import com.gymapp.main.network.ApiManagerInterface
import java.lang.Exception

open class BaseRepository(
    private val apiManager: ApiManagerInterface,
    private val gymDao: GymDao
) : BaseRepositoryInterface {

    private val countryMapper = CountryMapper()

    override suspend fun getCountries(): LiveData<List<Country>>? {

        val countries = gymDao.getCountriesList()

        if (!countries.value.isNullOrEmpty()) {
            return countries
        }

        saveCountries()

        return gymDao.getCountriesList()
    }

    override suspend fun saveCountries() {

        val countryQueryResponse = apiManager.getCountriesAsync().await()

        val countriesList = countryQueryResponse.data?.countries?.list

        if (countriesList.isNullOrEmpty()) {
            return
        }

        gymDao.insertCountries(countryMapper.mapToDtoList(countriesList as List<CountriesQuery.List>))
    }

}