package com.gymapp.features.profile.settings.domain

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface

class SettingsViewModel(val authRepositoryInterface: AuthRepositoryInterface) : BaseViewModel() {

    var hasSignedOut = MutableLiveData<Boolean>()

    fun signOut() {
        authRepositoryInterface.invalidateUserDataOnLogout()
        FirebaseAuth.getInstance().signOut()
        hasSignedOut.value = true
    }
}