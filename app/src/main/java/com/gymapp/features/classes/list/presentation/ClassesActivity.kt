package com.gymapp.features.classes.list.presentation

import android.os.Bundle
import android.provider.SyncStateContract
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.classes.list.domain.ClassesViewModel
import com.gymapp.helper.Constants
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ClassesActivity : BaseActivity(R.layout.activity_classes_list) {

    lateinit var classesViewModel: ClassesViewModel

    override fun setupViewModel() {
        classesViewModel = getViewModel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(intent.getBundleExtra(Constants.arguments).getString(Constants.classesListPageName)!!)
    }

    override fun bindViewModelObservers() {}

}