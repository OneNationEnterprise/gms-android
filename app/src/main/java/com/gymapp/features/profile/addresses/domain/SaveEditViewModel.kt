package com.gymapp.features.profile.addresses.domain

import android.annotation.SuppressLint
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.DynamicAddressFieldsInput
import com.apollographql.apollo.gym.type.SaveCustomerAddressInput
import com.cloudinary.utils.StringUtils
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.features.profile.addresses.presentation.saveedit.SaveEditVIew
import com.gymapp.helper.GoogleMapHelper
import com.gymapp.main.data.model.location.Address
import com.gymapp.main.data.model.user.AddressUser
import com.gymapp.main.data.model.user.DynamicAddressData
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface

class SaveEditViewModel(
    val userRepositoryInterface: UserRepositoryInterface,
    val configRepositoryInterface: ConfigRepositoryInterface
) : BaseViewModel() {

    private var customerAddress: AddressUser? = null
    private var addressResults: Address.Results? = null
    private var countryId: String = ""
    private var addressCoordinates: LatLng? = null
    private var countryIsoCode: String? = null
    lateinit var saveEditVIew: SaveEditVIew


    fun fetchDataEditAddress(addressId: String?) {

        customerAddress = userRepositoryInterface.getCurrentUser()?.address?.first {
            it.id == addressId
        }

        if (customerAddress == null) {
            saveEditVIew.showUnexpectedError()
            return
        }

        addressCoordinates = LatLng(
            customerAddress!!.geolocation.coordinates!![1]!!,
            customerAddress!!.geolocation.coordinates!![0]!!
        )

        countryId = customerAddress!!.countryId


        saveEditVIew.initEditAddressView(customerAddress!!)
    }


    fun fetchDataNewAddress(countryCode: String, latLng: LatLng) {

        countryIsoCode = countryCode

        addressCoordinates = latLng

        if (countryIsoCode.isNullOrEmpty()) {
            saveEditVIew.showUnexpectedError()
            return
        }

        initCreateAddressView(countryIsoCode!!)
    }

    private fun initCreateAddressView(
        countryIsoCode: String
    ) {
        val dynamicFields = getCountryAddressDynamicFields(countryIsoCode)
        saveEditVIew.initCreateAddressView(dynamicFields)
    }


//    @SuppressLint("CheckResult")
//    suspend fun onAddressDeleteClick() {
//
//        compositeDisposable += dataManager.deleteAddress(customerAddress!!.id())
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .dropBreadcrumb()
//            .subscribe({
//                if (it.errors().size == 0) {
//                    EventBus.getDefault().post(EventBusMessage.AddressHasBeenDeleted(customerAddress!!.id()))
//                    updateCustomer(null)
//                } else {
//                   saveEditVIew.onActionError(it.errors()[0].message())
//                }
//            }, {
//               saveEditVIew.onActionError(it.message)
//            })
//    }

    @SuppressLint("CheckResult")
    private suspend fun updateCustomer() {

        val error =
            userRepositoryInterface.saveUserDetailsByEmail(FirebaseAuth.getInstance().currentUser?.email!!)

        if (error.isNullOrEmpty()) {
            saveEditVIew.onActionSuccess()
        } else {
            saveEditVIew.onActionError(error)
        }
    }

    private fun getCountryAddressDynamicFields(countryIsoCode: String?): List<DynamicAddressData>? {

        if (StringUtils.isEmpty(countryIsoCode)) {
            return null
        }

        val country = configRepositoryInterface.getCountries().first {
            it.isoCode == countryIsoCode
        }

        countryId = country.countryId

        val addressFieldsList = country.addresses

        if (addressFieldsList.isEmpty()) {
            return null
        }

        return addressFieldsList
    }

    @SuppressLint("CheckResult")
    suspend fun saveAddress(dynamicFields: MutableList<DynamicAddressFieldsInput>) {

        val input = SaveCustomerAddressInput(
            id = Input.fromNullable(customerAddress?.id),
            isDefault = false,
            countryId = countryId,
            latitude = addressCoordinates!!.latitude,
            longitude = addressCoordinates!!.longitude,
            dynamicData = Input.fromNullable(dynamicFields)
        )

        val error = userRepositoryInterface.saveAddress(input)

        if (error.isNullOrEmpty()) {
            updateCustomer()
            return
        }

        saveEditVIew.onActionError(error)
    }

}