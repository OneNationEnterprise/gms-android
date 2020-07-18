package com.gymapp.features.homepage.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.GISLocationInput
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.homepage.data.HomepageRepositoryInterface
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.user.User

class HomepageViewModel(val homepageRepository: HomepageRepositoryInterface) : BaseViewModel() {

    var user: LiveData<User> = homepageRepository.getCurrentUser()
    var errorListingGyms = MutableLiveData<String?>()
    lateinit var nearByGyms : LiveData<List<Gym>>


    suspend fun fetchGymList() {

        val filter = GymsInRadiusFilter(
            GISLocationInput(
                UserCurrentLocalization.position.longitude,
                UserCurrentLocalization.position.latitude
            ),
            radius = 5000.0
        )

        errorListingGyms.value = homepageRepository.saveGymList(filter)

        nearByGyms = homepageRepository.getNearbyGyms()
    }


}