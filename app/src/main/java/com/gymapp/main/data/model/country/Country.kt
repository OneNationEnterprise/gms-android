package com.gymapp.main.data.model.country

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class Country(
    @PrimaryKey
    val id: String,
    val isoCode: String,
    val dialCode: String,
    val name: String,
    val flagPhoto: String
)