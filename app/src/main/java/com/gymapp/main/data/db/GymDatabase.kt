package com.gymapp.main.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gymapp.helper.Constants
import com.gymapp.main.data.model.brand.Brand
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.model.gym.Gym
import com.gymapp.main.data.model.gym.GymAddress
import com.gymapp.main.data.model.user.User
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Country::class,
        User::class,
        Gym::class,
        GymAddress::class,
        Brand::class
    ], version = 1, exportSchema = false
)
abstract class GymDatabase : RoomDatabase() {

    abstract fun gymDao(): GymDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}