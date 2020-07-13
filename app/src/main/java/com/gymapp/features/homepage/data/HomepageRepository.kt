package com.gymapp.features.homepage.data

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.data.BaseRepository
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.gym.GymMapper
import com.gymapp.main.data.model.user.UserByEmailMapper
import com.gymapp.main.network.ApiManagerInterface

class HomepageRepository(private val apiManager: ApiManagerInterface, private val gymDao: GymDao) :
    BaseRepository(apiManager, gymDao), HomepageRepositoryInterface {

    private val gymMapper = GymMapper()

    override suspend fun saveGymList(input: Input<GymsInRadiusFilter>): String? {
        val gymsInRadiusResponse = apiManager.getGymsInRadiusAsync(input).await()

        if ((gymsInRadiusResponse.errors != null && gymsInRadiusResponse.errors!!.isNotEmpty())
            || gymsInRadiusResponse.data == null || gymsInRadiusResponse.data?.gymsInRadius?.list == null
        ) {
            return "Error on getting nearby gyms"
        }
        val list = gymsInRadiusResponse.data!!.gymsInRadius.list

//        gymDao.insertNearbyGyms(gymMapper.mapToDtoList(list!!))
        return null
    }

//    override fun getGymDetailFromRemote(gymId: String): LiveData<Gym> {
//        return LiveData<Gym>()
//    }


}