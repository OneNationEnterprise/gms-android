package com.gymapp.main.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val email: String,
    val contactNumber: String?,
    val countryId: String,
    val photo: String?
)