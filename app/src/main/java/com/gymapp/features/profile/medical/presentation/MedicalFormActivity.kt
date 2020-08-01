package com.gymapp.features.profile.medical.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.gym.type.CustomerMedicalFormField
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.gymdetail.presentation.adapter.ClassCategoriesAdapter
import com.gymapp.features.profile.medical.domain.MedicalFormViewModel
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormAdapter
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormItemListener
import com.gymapp.features.profile.medical.presentation.view.MedicalFormCustomView
import com.gymapp.helper.MedicalFormRecycleViewItemType
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_gym_detail.*
import kotlinx.android.synthetic.main.activity_medical_form.*
import kotlinx.android.synthetic.main.activity_save_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MedicalFormActivity : BaseActivity(R.layout.activity_medical_form), MedicalFormItemListener {

    lateinit var medicalFormViewModel: MedicalFormViewModel
    var customViewsList = ArrayList<MedicalFormCustomView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(getString(R.string.medical_form_page_title))

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

            for (item in it) {
                val view = MedicalFormCustomView(this, item, this)

                if (item.getType() == MedicalFormRecycleViewItemType.TEXTBOX || item.getType() == MedicalFormRecycleViewItemType.CHECKBOX) {
                    customViewsList.add(view)
                }

                medicalFormContainer.addView(
                    view
                )
            }

            loading.visibility = View.GONE
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

    private fun saveFields() {
        loading.visibility = View.VISIBLE

        for (item in customViewsList) {
            if (item.isRequired && item.value.isNullOrEmpty()) {
                item.showTextBoxError()
                medicalFormViewModel.clearContentElementsList()
                dismissLoadingState()
                return
            }
            medicalFormViewModel.saveField(CustomerMedicalFormField(item.id, item.value ?: ""))
        }
    }

    override fun onDestroy() {
        MedicalFormAdapter.saveField = false
        super.onDestroy()
    }

    override fun dismissLoadingState() {
        loading.visibility = View.GONE
    }

    override fun onSaveClicked() {
        saveFields()
    }
}