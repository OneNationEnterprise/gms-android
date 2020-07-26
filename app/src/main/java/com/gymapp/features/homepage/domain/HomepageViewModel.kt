package com.gymapp.features.homepage.domain

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.GISLocationInput
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.main.data.repository.gyms.GymsRepositoryInterface
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.main.data.model.brand.Brand
import com.gymapp.main.data.model.brand.HomepageBrandListItem
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.user.User

class HomepageViewModel(private val gymsRepositoryInterface: GymsRepositoryInterface, private val authRepositoryInterface: AuthRepositoryInterface) :
    BaseViewModel() {

    var user = MutableLiveData<User?>()
    var errorListingGyms = MutableLiveData<String?>()
    var gymBrandsList = MutableLiveData<MutableList<HomepageBrandListItem>>()
    var nearByGyms = MutableLiveData<List<Gym>>()

    suspend fun fetchGymList(context: Context) {

        gymsRepositoryInterface.setContextTemp(context)

        user.value = authRepositoryInterface.getCurrentUser()
        val filter = GymsInRadiusFilter(
            GISLocationInput(
                UserCurrentLocalization.position!!.longitude,
                UserCurrentLocalization.position!!.latitude
            ),
            radius = 15000.0
        )

        errorListingGyms.value = gymsRepositoryInterface.saveGymList(filter)

        nearByGyms.value = gymsRepositoryInterface.getNearbyGyms()
    }

    fun setupGymBrandsAdapter() {

        val homepageBrandsList: MutableList<HomepageBrandListItem> = ArrayList()
        val brandsList: MutableList<Brand> = ArrayList()

        for (gym in nearByGyms.value!!) {

            if (!brandsList.contains(gym.brand)) {
                brandsList.add(gym.brand)
                homepageBrandsList.add(
                    HomepageBrandListItem(
                        gym.brand,
                        gym.images[0]
                    )
                )
            } else {
                homepageBrandsList.map {
                    if(gym.brand.brandId == it.brand.brandId){
                        it.gymCount+=1
                    }
                }
            }
        }

        gymBrandsList.value = homepageBrandsList
    }

}