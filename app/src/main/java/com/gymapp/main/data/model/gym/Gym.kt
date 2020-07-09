package com.gymapp.main.data.model.gym

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gymapp.main.data.model.brand.Brand
import com.gymapp.main.data.model.country.Country

@Entity(tableName = "gym_table")
data class Gym(
    @PrimaryKey val id: String,
    val name: String,
    val address: GymAddress,
    val brand: Brand
)

@Entity(tableName = "gym_address_table")
data class GymAddress(@PrimaryKey val id: String, val country: Country, val unitNumber: String)