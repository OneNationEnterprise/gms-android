package com.gymapp.features.homepage.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.GISLocationInput
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.homepage.data.HomepageRepository
import com.gymapp.features.homepage.data.HomepageRepositoryInterface
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.main.data.model.user.User

class HomepageViewModel(val homepageRepository: HomepageRepositoryInterface) : BaseViewModel() {

    var user: LiveData<User> = homepageRepository.getCurrentUser()
    val errorListingGyms = MutableLiveData<String?>()


    suspend fun fetchGymList() {

        val filter = GymsInRadiusFilter(
            GISLocationInput(
                UserCurrentLocalization.position.longitude,
                UserCurrentLocalization.position.latitude
            ),
            radius = 3000.0
        )

        val input = Input<GymsInRadiusFilter>(filter, true)

        val gymsList = homepageRepository.saveGymList(input)
    }


}