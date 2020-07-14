package com.gymapp.main.launcher.domain

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.launcher.data.LauncherRepositoryInterface

class LauncherViewModel(private val repository: LauncherRepositoryInterface) :
    BaseViewModel() {

    var errorFetchingUser = MutableLiveData<String?>()

    suspend fun fetchData() {
        repository.saveCountries()

        if (FirebaseAuth.getInstance().currentUser?.email != null) {
            errorFetchingUser.value =
                repository.saveUserDetailsByEmail(FirebaseAuth.getInstance().currentUser?.email!!)
        } else {
            errorFetchingUser.value = "User not found"
        }

    }



}