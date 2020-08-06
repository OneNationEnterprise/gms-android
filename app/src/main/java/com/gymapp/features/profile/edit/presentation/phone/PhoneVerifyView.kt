package com.gymapp.features.profile.edit.presentation.phone

import com.gymapp.main.data.model.country.Country

interface PhoneVerifyView {
    fun showLoading()
    fun hideLoading()
    fun showNotValidPhone(localizedMessage: String?)
    fun onSignSuccessResult()
    fun showErrorBanner(textMessage: String?)
    fun showModalBottomSheet(countries: List<Country>)
    fun setPhonePrefix(country: Country)
    fun setPhone(phone: String?)
    fun verifyPhoneNumber(phoneNumber: String, country: Country)
    fun closeActivity()
}