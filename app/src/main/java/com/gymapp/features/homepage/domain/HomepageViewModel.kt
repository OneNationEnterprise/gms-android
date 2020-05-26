package com.gymapp.features.homepage.domain

import androidx.lifecycle.LiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.homepage.data.HomepageRepository
import com.gymapp.features.homepage.data.HomepageRepositoryInterface
import com.gymapp.main.data.model.user.User

class HomepageViewModel(homepageRepository: HomepageRepositoryInterface) : BaseViewModel() {

    var user: LiveData<User> = homepageRepository.getCurrentUser()

}