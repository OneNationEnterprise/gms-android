package com.gymapp.main.data.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.data.BaseRepository
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.gym.GymMapper
import com.gymapp.main.network.ApiManagerInterface

class GymsRepository(private val apiManager: ApiManagerInterface, private val gymDao: GymDao) :
    BaseRepository(apiManager, gymDao),
    GymsRepositoryInterface {

    private val gymMapper = GymMapper()
    var nearbyGymsList = ArrayList<Gym>()

    override suspend fun saveGymList(input: GymsInRadiusFilter): String? {
        val gymsInRadiusResponse = apiManager.getGymsInRadiusAsync(input).await()

        if ((gymsInRadiusResponse.errors != null && gymsInRadiusResponse.errors!!.isNotEmpty())
            || gymsInRadiusResponse.data == null || gymsInRadiusResponse.data?.gymsInRadius?.list == null
        ) {
            return "Error on getting nearby gyms"
        }
        val list = gymsInRadiusResponse.data!!.gymsInRadius.list

        nearbyGymsList = gymMapper.mapToDtoList(list!!) as ArrayList<Gym>

//        gymDao.insertNearbyGyms(gymMapper.mapToDtoList(list!!))
        return null
    }

    override fun getNearbyGyms(): List<Gym> {
//        return gymDao.getNearbyGyms()

        val tempArrayList = ArrayList<Gym>()

        for (i in 0..10) {
            tempArrayList.add(nearbyGymsList[0])
        }
        return tempArrayList
    }


}