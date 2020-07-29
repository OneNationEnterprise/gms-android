package com.gymapp.features.profile.medical.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.ContentElementType
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.CheckBoxItem
import com.gymapp.features.profile.medical.data.items.GroupTitleItem
import com.gymapp.features.profile.medical.data.items.TextBoxItem
import com.gymapp.main.network.ApiManagerInterface

class MedicalFormViewModel(private val apiManagerInterface: ApiManagerInterface) : BaseViewModel() {


    val itemsForInMedicalFormAdapterList = MutableLiveData<List<MedicalFormListObject>>()

    suspend fun fetchData() {
        val apiResponse = apiManagerInterface.getMedicalFormAsync().await()

        if (!apiResponse.errors.isNullOrEmpty() || apiResponse.data == null) {
            return
        }

        val medicalFormData = apiResponse.data!!.getMedicalForm

        val itemsForInMedicalFormAdapterList = ArrayList<MedicalFormListObject>()

        for (item in medicalFormData) {
            itemsForInMedicalFormAdapterList.add(
                GroupTitleItem(item.name)
            )

            if (item.contentElements.isNullOrEmpty()) {
                continue
            }

            for (contentElement in item.contentElements) {
                when (contentElement?.type) {
                    ContentElementType.CHECKBOX -> {
                        itemsForInMedicalFormAdapterList.add(CheckBoxItem(contentElement))
                    }
                    ContentElementType.TEXTBOX -> {
                        itemsForInMedicalFormAdapterList.add(TextBoxItem(contentElement))
                    }

                }
            }
        }

        this.itemsForInMedicalFormAdapterList.postValue(itemsForInMedicalFormAdapterList)
    }

}

