package com.gymapp.main.data.repository.config

import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.fragment.CountryFields
import com.apollographql.apollo.gym.type.StatusType
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.country.CountryMapper
import com.gymapp.main.network.ApiManagerInterface

class ConfigRepository(private val apiManager: ApiManagerInterface) : ConfigRepositoryInterface {
    private val countryMapper = CountryMapper()
    var countriesList = ArrayList<Country>()


    override fun getCountries(): ArrayList<Country> {
//        return gymDao.getCountriesList()
        return countriesList
    }

    override suspend fun saveCountries() {

        val countryQueryResponse = apiManager.getCountriesAsync().await()

        val countriesList = countryQueryResponse.data?.countries?.list as List<CountriesQuery.List>

        if (countriesList.isNullOrEmpty()) {
            return
        }

        val countryFieldsList = ArrayList<CountryFields>()

        for (country in countriesList) {
            if (country.fragments.countryFields.status == StatusType.ACTIVE) {
                countryFieldsList.add(country.fragments.countryFields)
            }
        }

//        gymDao.insertCountries(countryMapper.mapToDtoList(countryFieldsList))

        this.countriesList = countryMapper.mapToDtoList(countryFieldsList) as ArrayList<Country>
    }
}