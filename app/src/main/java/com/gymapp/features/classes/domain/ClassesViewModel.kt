package com.gymapp.features.classes.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.GymClassesFilter
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.classes.data.ClassesRepositoryInterface
import com.gymapp.features.classes.list.presentation.adapter.ClassSelectedListener
import com.gymapp.main.data.model.classes.Class

class ClassesViewModel(private val classesRepositoryInterface: ClassesRepositoryInterface) :
    BaseViewModel(), ClassSelectedListener {

    val classesList = MutableLiveData<List<Class>?>()

    val classId = MutableLiveData<String>()

    val gymClass = MutableLiveData<Class?>()

    suspend fun fetchClassesList(gymId: String?, date: String, categoryId: String?) {
        val filter = Input.fromNullable(
            GymClassesFilter(
                gymId = Input.fromNullable(gymId),
                forDate = Input.fromNullable(date),
                gymClassCategoryId = Input.fromNullable(categoryId)
            )
        )

        classesList.postValue(classesRepositoryInterface.getClasses(filter))
    }

    override fun classSelectedListener(gymClass: Class) {
        classId.value = gymClass.id
    }

    fun fetchClassData(classId: String?) {
        if (classId.isNullOrEmpty()) {
            return
        }

        gymClass.value = classesRepositoryInterface.getClassFromCachedList(classId)
    }

}