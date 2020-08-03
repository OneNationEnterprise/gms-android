package com.gymapp.main.data.model.classes

import com.apollographql.apollo.gym.ClassesQuery
import com.apollographql.apollo.gym.fragment.ClassFields
import com.gymapp.base.data.BaseDataMapperInterface

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

    override fun mapToDtoList(input: List<ClassesQuery.List?>?): List<Class> {

        if (input.isNullOrEmpty()) return ArrayList()

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