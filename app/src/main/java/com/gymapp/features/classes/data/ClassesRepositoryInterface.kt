package com.gymapp.features.classes.data

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.ClassesQuery
import com.apollographql.apollo.gym.type.GymClassesFilter
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.model.classes.Class

interface ClassesRepositoryInterface : BaseRepositoryInterface {

    suspend fun getClasses(filter: Input<GymClassesFilter>): List<Class>?

    fun getClassFromCachedList(classId: String): Class?
}