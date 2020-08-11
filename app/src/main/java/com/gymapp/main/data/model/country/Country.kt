package com.gymapp.main.data.model.country

import com.gymapp.main.data.model.user.DynamicAddressData

//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity(tableName = "country_table")
data class Country(
//    @PrimaryKey
    val countryId: String,
    val isoCode: String,
    val dialCode: String,
    val name: String,
    val flagPhoto: String,
    val addresses: List<DynamicAddressData>
)