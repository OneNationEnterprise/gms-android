package com.gymapp.base.data

import androidx.lifecycle.LiveData
import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.fragment.CountryFields
//import com.gymapp.main.data.db.GymDao
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.country.CountryMapper
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.user.User
import com.gymapp.main.data.model.user.UserByEmailMapper
import com.gymapp.main.network.ApiManagerInterface

open class BaseRepository(
    private val apiManager: ApiManagerInterface
//    private val gymDao: GymDao
) : BaseRepositoryInterface {

    private val countryMapper = CountryMapper()
    private val userMapper = UserByEmailMapper()

    var countriesList = ArrayList<Country>()
    var user: User? = null

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
            countryFieldsList.add(country.fragments.countryFields)
        }

//        gymDao.insertCountries(countryMapper.mapToDtoList(countryFieldsList))

        this.countriesList = countryMapper.mapToDtoList(countryFieldsList) as ArrayList<Country>
    }

    override suspend fun saveUserDetailsByEmail(email: String): String? {

        val userDetailsResponse = apiManager.getUserDetailsByEmailAsync(email).await()

        if (userDetailsResponse.errors != null && userDetailsResponse.errors!!.isNotEmpty()
            || userDetailsResponse.data == null
            || (userDetailsResponse.data!!.customerByEmail == null)
        ) {
            return "Error on getting user details"
        }

//        gymDao.insertUser(userMapper.mapToDto(userDetailsResponse.data!!.customerByEmail!!))

        user = userMapper.mapToDto(userDetailsResponse.data!!.customerByEmail!!)
        return null
    }

    override fun getCurrentUser(): User? {
        return user
    }

}