package com.gymapp.features.profile.medical.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.gymdetail.presentation.adapter.ClassCategoriesAdapter
import com.gymapp.features.profile.medical.domain.MedicalFormViewModel
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormAdapter
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_gym_detail.*
import kotlinx.android.synthetic.main.activity_medical_form.*
import kotlinx.android.synthetic.main.activity_save_card.*
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

        loading.visibility = View.VISIBLE

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
            loading.visibility = View.GONE
        })

        medicalFormViewModel.notifyAdapterToSaveFields.observe(this, Observer {
            loading.visibility = View.VISIBLE

            MedicalFormAdapter.saveField = true

            for (i in 0 until it) {
                medicalFormAdapter.notifyItemChanged(i)
            }
        })

        medicalFormViewModel.dismissLoadingState.observe(this, Observer {
            loading.visibility = View.GONE
        })

        medicalFormViewModel.notifyActivityForSuspendFunctionScope.observe(this, Observer {
            GlobalScope.launch {
                medicalFormViewModel.saveMedicalFormData(it)
            }
        })

        medicalFormViewModel.showErrorBanner.observe(this, Observer {
            loading.visibility = View.GONE
            InAppBannerNotification.showErrorNotification(container, this, it)
        })

        medicalFormViewModel.showSuccessBanner.observe(this, Observer {
            loading.visibility = View.GONE
            InAppBannerNotification.showSuccessNotification(
                container,
                this,
                getString(R.string.form_saved)
            )
        })
    }

    override fun onDestroy() {
        MedicalFormAdapter.saveField = false
        super.onDestroy()
    }

    private fun initAdapter() {

        medicalFormAdapter = MedicalFormAdapter(ArrayList(), medicalFormViewModel)

        medicalFormAdapter.setHasStableIds(true)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        medicalFormRv.apply {
            adapter = medicalFormAdapter
            layoutManager = linearLayoutManager
        }
    }
}