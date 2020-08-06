package com.gymapp.features.profile.edit.domain

import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.profile.edit.presentation.phone.PhoneVerifyView
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface

class PhoneVerifyViewModel(val configRepositoryInterface: ConfigRepositoryInterface) : BaseViewModel() {

    lateinit var phoneVerifyView: PhoneVerifyView
    private lateinit var country: Country
    private lateinit var auth: FirebaseAuth
    private var phoneNumber: String = ""

    fun openPhonePrefixModal() {
        phoneVerifyView.showModalBottomSheet(configRepositoryInterface.getCountries())
    }
}