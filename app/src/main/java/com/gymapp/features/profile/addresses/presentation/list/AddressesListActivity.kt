package com.gymapp.features.profile.addresses.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.addresses.domain.AddressesListViewModel
import com.gymapp.features.profile.addresses.presentation.adapter.AddressesListAdapter
import com.gymapp.features.profile.addresses.presentation.saveedit.SaveEditAddressActivity
import com.gymapp.features.profile.addresses.presentation.saveedit.SelectAddressActivity
import com.gymapp.helper.Constants
import com.gymapp.main.data.model.user.AddressUser
import kotlinx.android.synthetic.main.activity_addresses_list.*
import kotlinx.android.synthetic.main.item_toolbar.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddressesListActivity : BaseActivity(R.layout.activity_addresses_list),
    AddressesListView {

    lateinit var addressesListViewModel: AddressesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Addresses")
        addressesListViewModel.addressView = this
    }

    override fun onResume() {
        super.onResume()
        addressesListViewModel.getAddressesList()
    }

    override fun bindViewModelObservers() {}

    override fun setupViewModel() {
        addressesListViewModel = getViewModel()
    }

    override fun onAddressClick(id: String) {
        val intent = Intent(this, SaveEditAddressActivity::class.java)
        intent.putExtra(Constants.addressId, id)
        startActivity(intent)
    }

    override fun onAddAddressClick() {
        val intent = Intent(this, SelectAddressActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        startActivity(intent)
    }


    override fun initRecycleView(addressesList: List<AddressUser>) {
        val nearbyRvAdapter =
            AddressesListAdapter(
                addressesList,
                this
            )
        addressesRv.apply {
            adapter = nearbyRvAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}