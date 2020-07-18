package com.gymapp.features.homepage.data

import androidx.lifecycle.LiveData
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.model.gym.Gym

interface HomepageRepositoryInterface : BaseRepositoryInterface {
    /**
     * save nearby gyms to database
     * returns error message (null if successful)
     */
    suspend fun saveGymList(input: GymsInRadiusFilter): String?

    /**
     * returns saved gyms from nearby
     */
    fun getGymDetailFromRemote(gymId: String): LiveData<List<Gym>>
}