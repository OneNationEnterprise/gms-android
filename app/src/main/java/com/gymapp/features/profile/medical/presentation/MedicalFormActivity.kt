package com.gymapp.features.profile.medical.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.medical.domain.MedicalFormViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MedicalFormActivity : BaseActivity(R.layout.activity_medical_form) {

    lateinit var medicalFormViewModel: MedicalFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(getString(R.string.medical_form_page_title))

        GlobalScope.launch {
            medicalFormViewModel.fetchData()
        }
    }

    override fun setupViewModel() {
        medicalFormViewModel = getViewModel()

    }

    override fun bindViewModelObservers() {
        medicalFormViewModel.itemsForInMedicalFormAdapterList.observe(this, Observer {
            it
        })
    }
}