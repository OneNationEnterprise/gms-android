package com.gymapp.features.profile.addresses.presentation.saveedit

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.DynamicAddressFieldsInput
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.addresses.domain.SaveEditViewModel
import com.gymapp.features.profile.addresses.presentation.InputFieldDynamicView
import com.gymapp.features.profile.addresses.presentation.list.AddressesListActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.ui.InAppBannerNotification
import com.gymapp.main.data.model.location.Address
import com.gymapp.main.data.model.user.AddressUser
import com.gymapp.main.data.model.user.DynamicAddressData
import kotlinx.android.synthetic.main.bottomsheet_dialog_delete_address.view.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.saveedit_address_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SaveEditAddressActivity : BaseActivity(R.layout.saveedit_address_activity), SaveEditVIew {

    lateinit var saveEditViewModel: SaveEditViewModel
    lateinit var activity: BaseActivity
    private val dynamicFields: MutableList<InputFieldDynamicView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveEditViewModel.saveEditVIew = this

        activity = this

        if (intent.getStringExtra(Constants.addressId) != null) {

            saveEditViewModel.fetchDataEditAddress(intent.getStringExtra(Constants.addressId))

            toolbarBin.visibility = View.VISIBLE

            toolbarBin.setOnClickListener {
                val view = layoutInflater.inflate(R.layout.bottomsheet_dialog_delete_address, null)
                val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
                dialog.setContentView(view)

                view.closeBtn.setOnClickListener {
                    dialog.dismiss()
                }
                view.addressDeleteBtn.setOnClickListener {
                    showLoading()
                    GlobalScope.launch {
                        saveEditViewModel.onAddressDeleteClick()
                    }
                    dialog.dismiss()
                }
                view.addressNotDeleteTv.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }

        } else if (intent.getStringExtra(Constants.locationAddressDetails) != null) {

            saveEditViewModel.fetchDataNewAddress(
                intent.getStringExtra(Constants.locationAddressDetails),
                intent.getParcelableExtra(Constants.latLng)
            )
        }

        setTitle("Save Address")


        addressSaveTv.setOnClickListener {

            val extraFieldsInput: MutableList<DynamicAddressFieldsInput> = ArrayList()
            for (inputFieldDynamicView in dynamicFields) {
                if (inputFieldDynamicView.isRequired() && inputFieldDynamicView.getValue()
                        .isEmpty()
                ) {
                    inputFieldDynamicView.showError()
                    return@setOnClickListener
                }
                extraFieldsInput.add(
                    DynamicAddressFieldsInput(
                        id = inputFieldDynamicView.getFieldId(),
                        value = Input.fromNullable(inputFieldDynamicView.getValue())
                    )
                )
            }

            showLoading()

            GlobalScope.launch {
                saveEditViewModel.saveAddress(extraFieldsInput)
            }
        }
    }

    override fun setupViewModel() {
        saveEditViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {

    }

    override fun initEditAddressView(
        addressDetails: AddressUser
    ) {

        dynamicFields.clear()

        if (addressDetails.dynamicData.isNullOrEmpty()) {
            return
        }

        for (addressField in addressDetails.dynamicData) {
            val inputFieldDynamicView = InputFieldDynamicView(this, addressField)
            dynamicFields.add(inputFieldDynamicView)
            dynamicFieldContainerLl.addView(inputFieldDynamicView)
        }
    }

    override fun initCreateAddressView(
        addressFields: List<DynamicAddressData>?
    ) {
        dynamicFields.clear()

        if (addressFields.isNullOrEmpty()) {
            return
        }

        for (addressField in addressFields) {
            val inputFieldDynamicView = InputFieldDynamicView(this, addressField)
            dynamicFields.add(inputFieldDynamicView)
            dynamicFieldContainerLl.addView(inputFieldDynamicView)
        }
    }

    override fun showUnexpectedError() {
        CoroutineScope(Dispatchers.Main).launch {
            InAppBannerNotification.showErrorNotification(
                saveEditAddressContainer,
                activity,
                getString(R.string.error_unknown)
            )
        }
    }

    override fun shouldSelectAnArea(countryCode: String, city: String?) {

    }

    override fun onActionSuccess() {

        CoroutineScope(Dispatchers.Main).launch {
            hideLoading()
            intent =
                Intent(
                    activity,
                    AddressesListActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)
            finish()
        }
    }

    override fun onActionError(message: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            hideLoading()
            activity
        }
    }

    override fun showLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            editAddressProgressBar.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            editAddressProgressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}