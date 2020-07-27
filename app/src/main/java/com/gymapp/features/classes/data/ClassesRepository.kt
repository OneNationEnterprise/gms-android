package com.gymapp.features.classes.data

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.GymClassesFilter
import com.gymapp.main.data.model.classes.Class
import com.gymapp.main.data.model.classes.ClassMapper
import com.gymapp.main.network.ApiManagerInterface

class ClassesRepository(private val apiManagerInterface: ApiManagerInterface) :
    ClassesRepositoryInterface {

    val classMapper = ClassMapper()
    var classesList: List<Class>? = ArrayList()

    override suspend fun getClasses(filter: Input<GymClassesFilter>): List<Class>? {
        val apiResponse = apiManagerInterface.getClassesAsync(filter).await()

        classesList = apiResponse.data?.gymClasses?.list?.let { classMapper.mapToDtoList(it) }

        return classesList
    }

    override fun getClassFromCachedList(classId: String): Class? {
        return classesList?.first {
            it.id == classId
        }
    }
}