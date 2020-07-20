package com.gymapp.helper

import com.google.android.gms.maps.model.LatLng

object UserCurrentLocalization {

    var position: LatLng? = null

    fun getUserCurrentLocalizationLatLng(): LatLng? {
        return position
    }

    fun setUserCurrentLocalization(position: LatLng) {
        this.position = position
    }
}