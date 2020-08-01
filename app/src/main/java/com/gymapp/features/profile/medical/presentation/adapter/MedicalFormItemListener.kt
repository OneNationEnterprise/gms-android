package com.gymapp.features.profile.medical.presentation.adapter

import com.apollographql.apollo.gym.type.CustomerMedicalFormField
import com.gymapp.features.profile.medical.data.MedicalFormListObject

interface MedicalFormItemListener {

    fun dismissLoadingState()
    fun onSaveClicked()
}