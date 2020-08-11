package com.gymapp.main.data.model.user

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val email: String,
    val contactNumber: String?,
    val country: UserCountry?,
    val photo: String?,
    val dob: String?,
    val address: List<AddressUser>
)

data class UserCountry(val id: String)

data class AddressUser(
    val id: String,
    val countryId: String,
    val geolocation: Geolocation,
    val dynamicData: List<DynamicAddressData>
)

data class DynamicAddressData(
    val id: String,
    val isRequired: Boolean,
    val name: String,
    val value: String?
)

data class Geolocation(val coordinates: List<Double?>?)