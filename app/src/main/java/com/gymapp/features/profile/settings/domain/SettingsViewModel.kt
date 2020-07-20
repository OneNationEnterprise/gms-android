package com.gymapp.features.profile.settings.domain

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.profile.settings.data.SettingsRepositoryInterface

class SettingsViewModel(val settingsRepository: SettingsRepositoryInterface) : BaseViewModel() {

    var hasSignedOut = MutableLiveData<Boolean>()

    fun signOut() {
        settingsRepository.invalidateUserDataOnLogout()
        FirebaseAuth.getInstance().signOut()
        hasSignedOut.value = true
    }
}