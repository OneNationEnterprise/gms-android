package com.gymapp.main.data.model.brand

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Brand(@PrimaryKey val id: String, val name: String, val phone: String, val gymCount: Int)