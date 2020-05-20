package com.gymapp.features.onboarding.auth.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.launcher.data.LauncherRepositoryInterface
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepositoryInterface) : BaseViewModel() {

    var countriesList: LiveData<List<Country>>? = null

    init {
        countriesList = repository.getCountries()
    }

    fun getAvailableCountries(): LiveData<List<Country>>? {
        return countriesList
    }

}