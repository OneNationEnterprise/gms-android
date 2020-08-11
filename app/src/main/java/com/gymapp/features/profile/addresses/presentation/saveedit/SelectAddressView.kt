package com.gymapp.features.profile.addresses.presentation.saveedit

interface SelectAddressView {
    fun showAddress(countryCode: String)
    fun addressNotFound(message: String)
    fun cofeNotDeliverOnThatLocation()
}