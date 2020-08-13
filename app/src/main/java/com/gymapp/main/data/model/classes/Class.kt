package com.gymapp.main.data.model.classes

import com.apollographql.apollo.gym.type.DifficultyLevel
import java.time.Duration

data class Class(
    val id: String,
    val images: List<String>?,
    val amount: String,
    val name: String,
    val description: String?,
    val difficultyLevel: DifficultyLevel,
    val spots: Int,
    val spotsAlotted: Int?,
    val estimatedCaloriesBurnt: String,
    val openTime: String,
    val duration: String,
    val instructor: Instructor?,
    val gymName: String?
)

data class Instructor(val firstName: String, val lastName: String?)
