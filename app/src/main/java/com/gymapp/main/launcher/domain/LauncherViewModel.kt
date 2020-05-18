package com.gymapp.main.launcher.domain

import androidx.lifecycle.viewModelScope
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.launcher.data.LauncherRepositoryInterface
import kotlinx.coroutines.launch

class LauncherViewModel(private val repository: LauncherRepositoryInterface) :
    BaseViewModel() {

    fun fetchData() {

        viewModelScope.launch {
            repository.saveCountries()
        }
    }

}