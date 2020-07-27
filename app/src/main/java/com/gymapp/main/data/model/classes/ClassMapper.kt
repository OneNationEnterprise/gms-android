package com.gymapp.main.data.model.classes

import com.apollographql.apollo.gym.ClassesQuery
import com.apollographql.apollo.gym.GymsInRadiusQuery
import com.apollographql.apollo.gym.fragment.ClassFields
import com.apollographql.apollo.gym.type.DifficultyLevel
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.main.data.model.gym.Gym

class ClassMapper : BaseDataMapperInterface<ClassesQuery.List, Class> {

    override fun mapToDto(input: ClassesQuery.List): Class {

        val classFields = input.fragments.classFields

        return Class(
            id = classFields.id,
            images = classFields.images,
            amount = classFields.amount.toString(),
            name = classFields.name,
            description = classFields.description,
            difficultyLevel = classFields.difficultyLevel,
            spots = classFields.spots,
            spotsAlotted = classFields.spotsAlotted,
            estimatedCaloriesBurnt = classFields.estimatedCaloriesBurnt,
            openTime = classFields.openTime.toString(),
            duration = classFields.duration.toString(),
            instructor = mapInstructor(classFields.instructor)

        )
    }

    override fun mapToDtoList(input: List<ClassesQuery.List?>): List<Class> {
        return input.map {
            mapToDto(it!!)
        }
    }

    fun mapInstructor(instructor: ClassFields.Instructor?): Instructor? {
        return if (instructor == null) {
            null
        } else {
            Instructor(instructor.firstName, instructor.lastName)
        }
    }
}