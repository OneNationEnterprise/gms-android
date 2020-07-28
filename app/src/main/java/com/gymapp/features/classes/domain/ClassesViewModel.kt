package com.gymapp.features.classes.domain

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.GymClassesFilter
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.classes.data.ClassesRepositoryInterface
import com.gymapp.features.classes.data.model.ClassDate
import com.gymapp.features.classes.list.presentation.adapter.ClassSelectedListener
import com.gymapp.main.data.model.classes.Class
import java.util.*
import kotlin.collections.ArrayList

class ClassesViewModel(private val classesRepositoryInterface: ClassesRepositoryInterface) :
    BaseViewModel(), ClassSelectedListener {

    val classesList = MutableLiveData<List<Class>?>()

    val datesList = MutableLiveData<ArrayList<ClassDate>>()

    val classId = MutableLiveData<String>()

    val classDate = MutableLiveData<ClassDate>()

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

    override fun onClassDateSelected(classDate: ClassDate) {
        this.classDate.value = classDate
    }

    fun fetchClassData(classId: String?) {
        if (classId.isNullOrEmpty()) {
            return
        }

        gymClass.value = classesRepositoryInterface.getClassFromCachedList(classId)
    }

    fun createDatesList() {

        val datesList = ArrayList<ClassDate>()

        var date = Date()
        val c = Calendar.getInstance()
        c.time = date

        date = c.time

        datesList.add(
            ClassDate(
                SimpleDateFormat("EEE, dd MMM", Locale.getDefault()).format(date),
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
            )
        )

        for (i in 1..10) {
            val c = Calendar.getInstance()
            c.add(Calendar.DATE, i)

            date = c.time

            datesList.add(
                ClassDate(
                    SimpleDateFormat("EEE, dd MMM", Locale.getDefault()).format(date),
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                )
            )
        }

        this.datesList.value = datesList
    }

}