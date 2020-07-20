package com.gymapp.features.mapview.domain

import androidx.lifecycle.MutableLiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.repository.GymsRepositoryInterface

class MapViewViewModel(val gymsRepositoryInterface: GymsRepositoryInterface) :
    BaseViewModel() {

    lateinit var cachedGymsList: List<Gym>
    var filteredGymsList = MutableLiveData<ArrayList<Gym>>()

    fun setupMapView(brandId: String?) {

        cachedGymsList = if (brandId == null) {
            gymsRepositoryInterface.getNearbyGyms()
        } else {
            val gymsByBrandIdList = ArrayList<Gym>()
            for (gym in gymsRepositoryInterface.getNearbyGyms()) {
                if (brandId == gym.brand.brandId) {
                    gymsByBrandIdList.add(gym)
                }
            }
            gymsByBrandIdList
        }

        filteredGymsList.postValue(cachedGymsList as ArrayList<Gym>)
    }


    fun updateFilteredGymsList(queryString: String) {
        val tempFilteredList = ArrayList<Gym>()

        for(gym in cachedGymsList){
            if(gym.name.contains(queryString)){
                tempFilteredList.add(gym)
            }
        }

        filteredGymsList.postValue(tempFilteredList)
    }
}

