package com.gymapp.features.profile.addresses.domain

import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.features.profile.addresses.presentation.list.AddressesListView
import com.gymapp.main.data.model.user.AddressUser

class AddressesListViewModel(val userRepositoryInterface: UserRepositoryInterface) :
    BaseViewModel() {

    lateinit var addressView: AddressesListView

    var addressesList = listOf<AddressUser>()

    fun getAddressesList() {
        val user = userRepositoryInterface.getCurrentUser() ?: return

        addressesList = user.address
        addressView.initRecycleView(addressesList)
    }


}