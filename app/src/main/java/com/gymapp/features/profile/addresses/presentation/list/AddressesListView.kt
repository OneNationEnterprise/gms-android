package com.gymapp.features.profile.addresses.presentation.list

import com.gymapp.main.data.model.user.AddressUser

interface AddressesListView {

    fun initRecycleView(addressesList: List<AddressUser>)
    fun onAddressClick(id: String)
    fun onAddAddressClick()
}