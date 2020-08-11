package com.gymapp.main.launcher.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.exception.ApolloHttpException
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface

class LauncherViewModel(
    private val configRepository: ConfigRepositoryInterface,
    private val userRepositoryInterface: UserRepositoryInterface
) :
    BaseViewModel() {

    var errorFetchingUser = MutableLiveData<String?>()

    suspend fun fetchData() {
        try {
            configRepository.saveCountries()
        } catch (e: ApolloHttpException) {

        }

        if (FirebaseAuth.getInstance().currentUser?.email != null) {
            try {
                errorFetchingUser.value =
                    userRepositoryInterface.saveUserDetailsByEmail(FirebaseAuth.getInstance().currentUser?.email!!)
            } catch (e: ApolloHttpException) {
            }
        } else {
            errorFetchingUser.value = "User not found"
        }

    }


}