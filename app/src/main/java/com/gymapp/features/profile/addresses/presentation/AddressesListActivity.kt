package com.gymapp.features.profile.addresses.presentation

import android.os.Bundle
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.addresses.domain.AddressesListViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddressesListActivity : BaseActivity(R.layout.activity_addresses_list) {

    lateinit var addressesListViewModel: AddressesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Addresses")
    }

    override fun bindViewModelObservers() {

    }

    override fun setupViewModel() {
        addressesListViewModel = getViewModel()
    }
}