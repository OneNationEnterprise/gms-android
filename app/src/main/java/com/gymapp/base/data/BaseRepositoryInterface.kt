package com.gymapp.base.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.user.User

interface BaseRepositoryInterface {

    /**
     * returns available countries list from db or remote
     */
    fun getCountries(): ArrayList<Country>

    /**
     * save available countries list into db
     */
    suspend fun saveCountries()

    /**
     * save user details from server to room_db
     * returns error message (null if successful)
     */
    suspend fun saveUserDetailsByEmail(email: String): String?


    /**
     * returns current session user from Room
     */
    fun getCurrentUser():User?


}