package com.gymapp.features.profile.main.domain

import androidx.lifecycle.MutableLiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.main.data.model.user.User

class ProfileViewModel(userRepositoryInterface: UserRepositoryInterface) : BaseViewModel() {

    var user = MutableLiveData<User?>()

    init {
        user.value = userRepositoryInterface.getCurrentUser()
    }




}