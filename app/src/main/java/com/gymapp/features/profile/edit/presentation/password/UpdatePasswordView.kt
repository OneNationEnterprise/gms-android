package com.gymapp.features.profile.edit.presentation.password

interface UpdatePasswordView {

    fun passwordSuccessUpdated()
    fun showErrorBanner(message: String?)
}