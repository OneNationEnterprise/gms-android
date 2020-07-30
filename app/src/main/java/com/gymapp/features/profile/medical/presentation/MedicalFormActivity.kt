package com.gymapp.features.profile.medical.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.gymdetail.presentation.adapter.ClassCategoriesAdapter
import com.gymapp.features.profile.medical.domain.MedicalFormViewModel
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormAdapter
import kotlinx.android.synthetic.main.activity_gym_detail.*
import kotlinx.android.synthetic.main.activity_medical_form.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MedicalFormActivity : BaseActivity(R.layout.activity_medical_form) {

    lateinit var medicalFormViewModel: MedicalFormViewModel
    lateinit var medicalFormAdapter: MedicalFormAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(getString(R.string.medical_form_page_title))

        initAdapter()

        GlobalScope.launch {
            medicalFormViewModel.fetchData()
        }
    }

    override fun setupViewModel() {
        medicalFormViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        medicalFormViewModel.itemsForInMedicalFormAdapterList.observe(this, Observer {

            medicalFormAdapter.updateList(it)
        })
    }

    private fun initAdapter() {

        medicalFormAdapter = MedicalFormAdapter(ArrayList())

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        medicalFormRv.apply {
            adapter = medicalFormAdapter
            layoutManager = linearLayoutManager
        }
    }
}