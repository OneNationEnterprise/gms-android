package com.gymapp.features.profile.edit.domain

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.SaveCustomerInput
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.features.profile.edit.presentation.password.UpdatePasswordView

class UpdatePasswordViewModel(private val authRepositoryInterface: AuthRepositoryInterface): BaseViewModel() {

    lateinit var listener : UpdatePasswordView

    suspend  fun updatePassword(newPassword: String) {

        val profileDetails = authRepositoryInterface.getCurrentUser() ?: return

        val error = authRepositoryInterface.saveCustomer(
            SaveCustomerInput(
                profileDetails.firstName,
                profileDetails.lastName,
                profileDetails.email,
                profileDetails.contactNumber!!,
                Input.fromNullable(null),
                Input.fromNullable(profileDetails.photo),
                Input.fromNullable(profileDetails.dob)
            )
        )

        if (error.isNullOrEmpty()) {
            listener.passwordSuccessUpdated()
        } else {
            listener.showErrorBanner(error)
        }
    }
}