package com.gymapp.helper

import com.google.android.gms.maps.model.LatLng


object UserCurrentLocalization {

    lateinit var position: LatLng

    fun getUserCurrentLocalizationLatLng(): LatLng? {
        if (this::position.isInitialized) {
            return position
        }
        return null
    }
}