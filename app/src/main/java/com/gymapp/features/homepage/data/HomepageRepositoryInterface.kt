package com.gymapp.features.homepage.data

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.model.gym.Gym

interface HomepageRepositoryInterface : BaseRepositoryInterface {
    /**
     * save user details from server to room_db
     * returns error message (null if successful)
     */
    suspend fun saveGymList(input: Input<GymsInRadiusFilter>): String?

    /**
     * returns current session user from Room
     */
//    fun getGymDetailFromRemote(gymId: String): LiveData<Gym>
}