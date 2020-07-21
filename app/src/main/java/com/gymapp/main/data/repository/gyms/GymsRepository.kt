package com.gymapp.main.data.repository.gyms

//import com.gymapp.main.data.db.GymDao
import android.content.Context
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gymapp.base.data.BaseRepository
import com.gymapp.helper.Temporary
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.gym.GymMapper
import com.gymapp.main.network.ApiManagerInterface


class GymsRepository(private val apiManager: ApiManagerInterface/*, private val gymDao: GymDao*/) :
    BaseRepository(apiManager/*, gymDao*/),
    GymsRepositoryInterface {

    private val gymMapper = GymMapper()
    var nearbyGymsList = ArrayList<Gym>()

    //    TODO delete this
    lateinit var context: Context

    override fun setContextTemp(context: Context) {
        this.context = context
    }

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

        //TODO delete this
        var json: String = ""
        for (i in 1..4) {
            when (i) {
                1 -> {
                     json = Temporary.readFileFromAssets(context, "json/gym1.json")!!
                }
                2 -> {
                     json = Temporary.readFileFromAssets(context, "json/gym2.json")!!
                }
                3 -> {
                     json = Temporary.readFileFromAssets(context, "json/gym3.json")!!
                }
                4 -> {
                     json = Temporary.readFileFromAssets(context, "json/gym4.json")!!
                }
            }

            val gym = Gson().fromJson<Gym>(
                json,
                object : TypeToken<Gym>() {}.type
            )

            tempArrayList.add(gym)
        }

        return tempArrayList
//        return nearbyGymsList
    }


}