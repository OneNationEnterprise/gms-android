package com.gymapp.features.gymdetail.domain

import androidx.lifecycle.MutableLiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.repository.gyms.GymsRepositoryInterface

class GymDetailViewModel(val gymsRepositoryInterface: GymsRepositoryInterface) :
    BaseViewModel() {

    val gym = MutableLiveData<Gym>()

    fun setupGymData(gymId: String?) {

        if (gymId.isNullOrEmpty()) {
            //TODO setup error state
            return
        }

        gym.value = gymsRepositoryInterface.getNearbyGyms().first {
            it.gymId == gymId
        }
    }

}