package com.gymapp.helper

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng
import com.gymapp.main.data.model.location.Address

object GoogleMapHelper {


    fun getArea(address: Address.Results): String? {

        for (i in 0..address.results!!.size - 1) {
            if (address.results[i].types!!.contains("premise")) {
                var subLocality = address.results[i].addressComponents?.find { it.types!!.contains("sublocality") }
                if (subLocality != null) {
                    return subLocality.longName
                }
//                else {
//                    subLocality = address.results[i].addressComponents?.find { it.types!!.contains("locality") }
//                    if (subLocality != null) {
//                        return subLocality.longName
//                    }
//                }
            } else if (address.results[i].types!!.contains("route")) {
                var subLocality = address.results[i].addressComponents?.find { it.types!!.contains("sublocality") }
                if (subLocality != null) {
                    return subLocality.longName
                }
//                else {
//                    subLocality = address.results[i].addressComponents?.find { it.types!!.contains("locality") }
//                    if (subLocality != null) {
//                        return subLocality.longName
//                    }
//                }
            } else if (address.results[i].types!!.contains("political") || address.results[i].types!!.contains("sublocality")) {
                var subLocality = address.results[i].addressComponents!!.find { it.types!!.contains("sublocality") }
                if (subLocality != null) {
                    return subLocality.longName
                }
//                else {
//                    subLocality = address.results[i].addressComponents?.find { it.types!!.contains("locality") }
//                    if (subLocality != null) {
//                        return subLocality.longName
//                    }
//                }
            }
        }
        return ""
    }

    fun getCity(address: Address.Results): String? {

        for (i in 0..address.results!!.size - 1) {
            if (address.results[i].types!!.contains("premise")) {

                var locality = address.results[i].addressComponents?.find { it.types!!.contains("locality") }
                if (locality != null) {
                    return locality.longName
                }
            } else if (address.results[i].types!!.contains("route")) {

                var locality = address.results[i].addressComponents?.find { it.types!!.contains("locality") }
                if (locality != null) {
                    return locality.longName
                }
            } else if (address.results[i].types!!.contains("political") || address.results[i].types!!.contains("locality")) {

                var locality = address.results[i].addressComponents?.find { it.types!!.contains("locality") }
                if (locality != null) {
                    return locality.longName
                }
            }
        }
        return ""
    }

    fun getLatLng(address: Address.Results): LatLng? {

        var latLng: LatLng? = null
        for (i in 0..address.results!!.size - 1) {
            if (address.results[i].types!!.contains("route") /*|| address.results[i].types!!.contains("political")*/) {
                latLng = LatLng(address.results[i].geometry!!.location!!.lat,
                    address.results[i].geometry!!.location!!.lng)
            }
        }
        return latLng
    }

    fun getBlock(address: Address.Results): String? {

        for (i in 0..address.results!!.size - 1) {
            if (address.results[i].types!!.contains("neighborhood")) {
                val block = address.results[i].addressComponents?.find { it.types!!.contains("neighborhood") }
                if (block != null) {
                    return block.longName
                }
            }
        }
        return ""
    }

    fun getStreet(address: Address.Results): String? {

        for (i in 0..address.results!!.size - 1) {
            if (address.results[i].types!!.contains("street_number")) {
                val streetName = address.results[i].addressComponents?.find { it.types!!.contains("route") }
                if (streetName != null) {
                    return streetName.longName
                }
            } else if (address.results[i].types!!.contains("route")) {
                val streetName = address.results[i].addressComponents?.find { it.types!!.contains("route") }
                if (streetName != null) {
                    return streetName.longName
                }
            }
        }
        return ""
    }

    fun getCountryCode(address: Address.Results): String? {

        for (i in 0 until address.results!!.size) {
            if (address.results[i].types!!.contains("country")) {
                val countryCode = address.results[i].addressComponents?.find { it.types!!.contains("country") }
                return countryCode?.shortName
            }
        }

        return ""
    }

    fun isLocationEnabled(mContext: Context): Boolean {
        val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }


    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    fun distance(lat1: Double, lon1: Double, lat2: Double,
                 lon2: Double, el1: Double = 0.0, el2: Double = 0.0): Double {

        val R = 6371 // Radius of the earth

        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        var distance = R.toDouble() * c * 1000.0 // convert to meters

        val height = el1 - el2

        distance = Math.pow(distance, 2.0) + Math.pow(height, 2.0)

        return Math.sqrt(distance)
    }

}