package com.gymapp.features.classes.list.presentation.adapter

import com.gymapp.features.classes.data.model.ClassDate
import com.gymapp.main.data.model.classes.Class

interface ClassSelectedListener {

    fun classSelectedListener(gymClass: Class)
    fun onClassDateSelected(classDate: ClassDate)
}