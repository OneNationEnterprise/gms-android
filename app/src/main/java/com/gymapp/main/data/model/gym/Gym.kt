package com.gymapp.main.data.model.gym

//import androidx.room.Embedded
//import androidx.room.Entity
//import androidx.room.Ignore
//import androidx.room.PrimaryKey
import com.gymapp.main.data.model.brand.Brand
import com.gymapp.main.data.model.country.Country

//@Entity(tableName = "gym_table")
data class Gym(
//    @PrimaryKey
    val gymId: String,
    val name: String,
//    @Embedded(prefix = "address_")
    val address: GymAddress,
//    @Ignore
    val images: List<String>,
//    @Embedded(prefix = "brand_")
    val brand: Brand

)

//@Entity(tableName = "gym_address_table")
data class GymAddress(
//    @PrimaryKey
    val gymAddressId: String,
//    @Embedded(prefix = "gym_address_")
    val countryId: String,
//    @Embedded(prefix = "gym_address_")
    val latitude: Double,
//    @Embedded(prefix = "gym_address_")
    val longitude: Double,
//    @Embedded(prefix = "gym_address_")
    val unitNumber: String

)