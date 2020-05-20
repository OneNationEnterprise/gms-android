package com.gymapp.main.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gymapp.main.data.model.country.Country

@Dao
interface GymDao  {

    @Query("SELECT * from country_table")
    fun getCountriesList(): LiveData<List<Country>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountries(country: List<Country>)

}