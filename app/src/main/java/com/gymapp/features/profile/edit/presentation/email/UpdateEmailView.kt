package com.gymapp.features.profile.edit.presentation.email

interface UpdateEmailView {
    fun updateEmail(email: String, password: String)
    fun showError(error: String?)
}