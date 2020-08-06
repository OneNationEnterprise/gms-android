package com.gymapp.features.profile.edit.presentation.profile

import org.jetbrains.annotations.Nullable

interface EditProfileViewInterface {
    fun setFirstName(firstName: String)
    fun setLastName(lastName: String)
    fun setPhone(phone: String?)
    fun setEmail(email: String?)
    fun setBirthday(birthday: String)
    fun setPhoto(photoUrl: String)
    fun setInitials(initials: String)
    fun changesSavedSuccesfully()
    fun setVerifiedPhoneStatus(isVerified: Boolean)
    fun showLoading()
    fun hideLoading()
    fun showErrorBanner(textMessage: String?)
    fun setPasswordOnClickListener(email: String?)
}