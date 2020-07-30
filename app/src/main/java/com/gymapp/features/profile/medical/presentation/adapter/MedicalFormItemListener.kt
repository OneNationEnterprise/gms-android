package com.gymapp.features.profile.medical.presentation.adapter

import com.apollographql.apollo.gym.type.CustomerMedicalFormField
import com.gymapp.features.profile.medical.data.MedicalFormListObject

interface MedicalFormItemListener {

    /**
     * @param isFieldValid - if the field is not optional and it has valid data etc.
     * @param isLastField - if this validated field is the last one from recycle view
     */
    fun saveField(
        field: CustomerMedicalFormField
    )

    fun updateFieldValue(position: Int, item: MedicalFormListObject)

    fun notifyAdapterToSaveFields()

    fun dismissLoadingState()
}