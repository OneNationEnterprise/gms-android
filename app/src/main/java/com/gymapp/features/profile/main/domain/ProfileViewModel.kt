package com.gymapp.features.profile.main.domain

import androidx.lifecycle.LiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.profile.main.data.ProfileRepositoryInterface
import com.gymapp.main.data.model.user.User

class ProfileViewModel(profileRepository: ProfileRepositoryInterface) : BaseViewModel() {

    var user: LiveData<User> = profileRepository.getCurrentUser()
}