package com.gymapp.main.data.model.country

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey
    val id: String,
    val isoCode: String,
    val dialCode: String,
    val name: String,
    val flagPhoto: String
)