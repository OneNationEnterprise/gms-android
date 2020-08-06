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
    val dob: String?
)

data class UserCountry(val id: String)