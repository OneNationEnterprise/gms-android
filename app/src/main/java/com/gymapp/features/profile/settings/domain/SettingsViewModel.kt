package com.gymapp.features.profile.settings.domain

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface

class SettingsViewModel(val userRepositoryInterface: UserRepositoryInterface) : BaseViewModel() {

    var hasSignedOut = MutableLiveData<Boolean>()

    fun signOut() {
        userRepositoryInterface.invalidateUserDataOnLogout()
        FirebaseAuth.getInstance().signOut()
        hasSignedOut.value = true
    }
}