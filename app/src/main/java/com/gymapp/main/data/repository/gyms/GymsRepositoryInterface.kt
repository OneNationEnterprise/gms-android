package com.gymapp.main.data.repository.gyms

import android.content.Context
import com.apollographql.apollo.gym.GymClassCategoriesQuery
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.model.gym.Gym

interface GymsRepositoryInterface : BaseRepositoryInterface {
    /**
     * save nearby gyms to database
     * returns error message (null if successful)
     */
    suspend fun saveGymList(input: GymsInRadiusFilter): String?


    /**
     * get saved gyms list from local Room database
     */
    fun getNearbyGyms(): List<Gym>

    /**
     * cache from remote and return list of available categories
     */
    suspend fun getGymCategories(gymId: String): List<GymClassCategoriesQuery.List?>?

    fun setContextTemp(context: Context)


}