package com.gymapp.main.data.model.brand

//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity
data class Brand(
//    @PrimaryKey
    val brandId: String,
    val name: String,
    val phone: String,
    val gymCount: Int,
    val logo: String
)