package com.gymapp.main.data.model.gym

import com.gymapp.main.data.model.brand.Brand

data class Gym(
    val gymId: String,
    val name: String,
    val address: GymAddress,
    val images: List<String>,
    val brand: Brand,
    val phone: String,
    val distance: Double?,
    val description: String?,
    val opening: Opening?,
    val amenityList: List<Amenity>?
)

data class GymAddress(
    val gymAddressId: String,
    val countryId: String,
    val geoLocation: Geolocation,
    val unitNumber: String

)

data class Geolocation(val coordinates: List<Double?>?)

data class Opening(val operatingTime: OperatingTime)

data class OperatingTime(val begin: String?, val end: String?)

data class Amenity(val icon: String?, val name: String)
