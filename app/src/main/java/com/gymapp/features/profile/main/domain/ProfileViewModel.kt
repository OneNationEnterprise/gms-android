package com.gymapp.features.profile.main.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.features.profile.main.data.ProfileRepositoryInterface
import com.gymapp.main.data.model.user.User

class ProfileViewModel(authRepositoryInterface: AuthRepositoryInterface) : BaseViewModel() {

    var user = MutableLiveData<User?>()

    init {
        user.value = authRepositoryInterface.getCurrentUser()
    }




}