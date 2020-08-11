package com.gymapp.features.profile.addresses.presentation.saveedit

import com.gymapp.main.data.model.location.Address
import com.gymapp.main.data.model.user.AddressUser
import com.gymapp.main.data.model.user.DynamicAddressData

interface SaveEditVIew {

    fun initEditAddressView(addressDetails: AddressUser)
    fun initCreateAddressView(addressFields: List<DynamicAddressData>?)
    fun showUnexpectedError()
    fun shouldSelectAnArea(countryCode: String, city: String?)
    fun onActionError(message: String?)
    fun showLoading()
    fun onActionSuccess()
}