package com.gymapp.features.profile.edit.presentation.changename

interface ChangeCustomerNameView {

    fun showNotValidFirstName()
    fun showNotValidLastName()
    fun showLoading()
    fun showErrorBanner(textMessage: String?)
    fun changesSavedSuccesfully()
    fun setValue(customerName: String)
}
