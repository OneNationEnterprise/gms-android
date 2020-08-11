package com.gymapp.features.profile.addresses.domain

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.profile.addresses.presentation.saveedit.SelectAddressView
import com.gymapp.helper.GoogleMapHelper
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface
import java.io.IOException
import java.util.*
import kotlin.NoSuchElementException

class SelectAddressViewModel(val configRepositoryInterface: ConfigRepositoryInterface) :
    BaseViewModel() {

    lateinit var selectAddressView: SelectAddressView

    @SuppressLint("CheckResult")
    fun fetchAddress(latitude: Double, longitude: Double, context: Context) {
        val geocoder = Geocoder(context, Locale.getDefault());
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1);
            val obj = addresses.get(0);

            try {
                configRepositoryInterface.getCountries().first() {
                    it.isoCode == obj.countryCode
                }

                selectAddressView.showAddress(obj.countryCode)

            } catch (error: NoSuchElementException) {
                selectAddressView.cofeNotDeliverOnThatLocation()
            }

        } catch (e: IOException) {
            selectAddressView.addressNotFound("Could not locate address")
        }


    }

    fun getAddress(lat: Double, lng: Double) {

    }
}