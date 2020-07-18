package com.gymapp.features.homepage.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.GISLocationInput
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.homepage.data.HomepageRepositoryInterface
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.main.data.model.brand.Brand
import com.gymapp.main.data.model.brand.HomepageBrandListItem
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.user.User
import java.lang.Exception

class HomepageViewModel(val homepageRepository: HomepageRepositoryInterface) : BaseViewModel() {

    var user: LiveData<User> = homepageRepository.getCurrentUser()
    var errorListingGyms = MutableLiveData<String?>()
    var gymBrandsList = MutableLiveData<MutableList<HomepageBrandListItem>>()
    var nearByGyms = MutableLiveData<List<Gym>>()

    suspend fun fetchGymList() {

        val filter = GymsInRadiusFilter(
            GISLocationInput(
                UserCurrentLocalization.position.longitude,
                UserCurrentLocalization.position.latitude
            ),
            radius = 5000.0
        )

//        /**
//         * if there are already gym locations saved in database don't query the server again
//         */
//        if(homepageRepository.getNearbyGyms().value!=null && homepageRepository.getNearbyGyms().value!!.isEmpty()){
        errorListingGyms.value = homepageRepository.saveGymList(filter)
//        }

        nearByGyms.value = homepageRepository.getNearbyGyms().value
    }

    fun setupGymBrandsAdapter() {

        val brandsList: MutableList<HomepageBrandListItem> = ArrayList()

//        if (!::nearByGyms.isInitialized) {
//            throw Throwable("NearByGyms should be initialized in this point")
//        }
//
//        for (gym in nearByGyms.value!!) {
//            brandsList.add(
//                HomepageBrandListItem(
//                    gym.brand,
//                    true
//                )
//            ) //TODO havePasses param should come from User obj
//        }

        gymBrandsList.value = brandsList
    }

}