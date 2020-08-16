package com.gymapp.features.profile.main.domain

import androidx.lifecycle.MutableLiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.main.data.model.user.User

class ProfileViewModel(val userRepositoryInterface: UserRepositoryInterface) : BaseViewModel() {

    var user = MutableLiveData<User?>()

    fun setProfile(){
        user.value = userRepositoryInterface.getCurrentUser()
    }




}