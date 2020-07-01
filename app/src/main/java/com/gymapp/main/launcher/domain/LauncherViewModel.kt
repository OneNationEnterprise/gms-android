package com.gymapp.main.launcher.domain

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.homepage.presentation.HomepageActivity
import com.gymapp.features.onboarding.OnBoardingActivity
import com.gymapp.main.launcher.data.LauncherRepositoryInterface
import kotlinx.coroutines.launch

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