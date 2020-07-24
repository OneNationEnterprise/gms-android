package com.gymapp.features.gymdetail.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.GymClassCategoriesQuery
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.repository.gyms.GymsRepositoryInterface

class GymDetailViewModel(val gymsRepositoryInterface: GymsRepositoryInterface) :
    BaseViewModel() {

    val gym = MutableLiveData<Gym>()

    val gymClassCategories = MutableLiveData<List<GymClassCategoriesQuery.List?>>()

    suspend fun setupGymData(gymId: String?) {

        if (gymId.isNullOrEmpty()) {
            //TODO setup error state
            return
        }

        gym.postValue(gymsRepositoryInterface.getNearbyGyms().first {
            it.gymId == gymId
        })

        getClassCategories()
    }


    private suspend fun getClassCategories() {
        val classCategories = gymsRepositoryInterface.getGymCategories()

        if (classCategories.isNullOrEmpty()) {
            return
        }

        gymClassCategories.postValue(classCategories)
    }

}