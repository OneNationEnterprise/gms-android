package com.gymapp.main.launcher.domain

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface
import com.gymapp.main.launcher.data.LauncherRepositoryInterface

class LauncherViewModel(private val configRepository: ConfigRepositoryInterface, private val authRepositoryInterface: AuthRepositoryInterface ) :
    BaseViewModel() {

    var errorFetchingUser = MutableLiveData<String?>()

    suspend fun fetchData() {
        configRepository.saveCountries()

        if (FirebaseAuth.getInstance().currentUser?.email != null) {
            errorFetchingUser.value =
                authRepositoryInterface.saveUserDetailsByEmail(FirebaseAuth.getInstance().currentUser?.email!!)
        } else {
            errorFetchingUser.value = "User not found"
        }

    }



}