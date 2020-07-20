package com.gymapp.features.homepage.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.GISLocationInput
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.data.repository.GymsRepositoryInterface
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.main.data.model.brand.HomepageBrandListItem
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.user.User

class HomepageViewModel(private val gymsRepositoryInterface: GymsRepositoryInterface) :
    BaseViewModel() {

    lateinit var user: MutableLiveData<User?>
    var errorListingGyms = MutableLiveData<String?>()
    var gymBrandsList = MutableLiveData<MutableList<HomepageBrandListItem>>()
    var nearByGyms = MutableLiveData<List<Gym>>()

    suspend fun fetchGymList() {

      user.value  = gymsRepositoryInterface.getCurrentUser()
        val filter = GymsInRadiusFilter(
            GISLocationInput(
                UserCurrentLocalization.position.longitude,
                UserCurrentLocalization.position.latitude
            ),
            radius = 5000.0
        )

        errorListingGyms.value = gymsRepositoryInterface.saveGymList(filter)

        nearByGyms.value = gymsRepositoryInterface.getNearbyGyms()
    }

    fun setupGymBrandsAdapter() {

        val brandsList: MutableList<HomepageBrandListItem> = ArrayList()

        for (gym in nearByGyms.value!!) {
            brandsList.add(
                HomepageBrandListItem(
                    gym.brand,
                    true
                )
            ) //TODO havePasses param should come from User obj
        }

        gymBrandsList.value = brandsList
    }

}