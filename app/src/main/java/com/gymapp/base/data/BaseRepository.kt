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

}