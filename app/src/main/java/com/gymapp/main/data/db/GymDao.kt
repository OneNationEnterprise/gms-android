package com.gymapp.main.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.user.User

@Dao
interface GymDao {

    @Query("SELECT * from country_table")
    fun getCountriesList(): LiveData<List<Country>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountries(country: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * from user_table")
    fun getCurrentUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNearbyGyms(gyms: List<Gym>)

}