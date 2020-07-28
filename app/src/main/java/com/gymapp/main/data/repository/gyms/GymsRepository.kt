package com.gymapp.main.data.repository.gyms

//import com.gymapp.main.data.db.GymDao
import android.content.Context
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.GymClassCategoriesQuery
import com.apollographql.apollo.gym.type.GlobalStatusType
import com.apollographql.apollo.gym.type.GymClassCategoryFilter
import com.apollographql.apollo.gym.type.GymsInRadiusFilter
import com.gymapp.base.data.BaseRepository
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

        return null
    }

    override fun getNearbyGyms(): List<Gym> {
        return nearbyGymsList
    }

    override suspend fun getGymCategories(gymId: String): List<GymClassCategoriesQuery.List?>? {

        val input = Input.fromNullable(GymClassCategoryFilter(
            status = Input.fromNullable(GlobalStatusType.ACTIVE),
            gymId = Input.fromNullable(gymId)
        ))

        val gymCategoriesResponse = apiManager.getGymCategoriesAsync(input).await()
        return gymCategoriesResponse.data?.gymClassCategories?.list
    }







    //////////////////////////// MOCK GYM DATA ////////////////////////

    /*        //TODO delete this
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

        return tempArrayList*/


}