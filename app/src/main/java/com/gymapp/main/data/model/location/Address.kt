package com.gymapp.main.data.model.location

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

object Address {

    data class Results(
        val results: ArrayList<ReverseGeocodingResponse>?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(parcel.createTypedArrayList(ReverseGeocodingResponse)) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeTypedList(results)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Results> {
            override fun createFromParcel(parcel: Parcel): Results {
                return Results(parcel)
            }

            override fun newArray(size: Int): Array<Results?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class ReverseGeocodingResponse(
        @SerializedName("address_components")
        val addressComponents: ArrayList<AddressComponents>?,
        val geometry: Geometry?,
        val types: ArrayList<String>?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(AddressComponents),
            parcel.readParcelable(Address.Geometry::class.java.classLoader),
            parcel.createStringArrayList()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeTypedList(addressComponents)
            parcel.writeParcelable(geometry, flags)
            parcel.writeStringList(types)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ReverseGeocodingResponse> {
            override fun createFromParcel(parcel: Parcel): ReverseGeocodingResponse {
                return ReverseGeocodingResponse(parcel)
            }

            override fun newArray(size: Int): Array<ReverseGeocodingResponse?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Geometry(
        val location: Location?,
        @SerializedName("location_type")
        val locationType: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readParcelable(Address.Location::class.java.classLoader),
            parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeParcelable(location, flags)
            parcel.writeString(locationType)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Geometry> {
            override fun createFromParcel(parcel: Parcel): Geometry {
                return Geometry(parcel)
            }

            override fun newArray(size: Int): Array<Geometry?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Location(
        val lat: Double,
        val lng: Double
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeDouble(lat)
            parcel.writeDouble(lng)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Location> {
            override fun createFromParcel(parcel: Parcel): Location {
                return Location(parcel)
            }

            override fun newArray(size: Int): Array<Location?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class AddressComponents(
        @SerializedName("long_name")
        val longName: String?,
        @SerializedName("short_name")
        val shortName: String?,
        val types: List<String>?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArrayList()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(longName)
            parcel.writeString(shortName)
            parcel.writeStringList(types)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<AddressComponents> {
            override fun createFromParcel(parcel: Parcel): AddressComponents {
                return AddressComponents(parcel)
            }

            override fun newArray(size: Int): Array<AddressComponents?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class DisplayAddress(
        val area: String?,
        val houseNumber: String?,
        val street: String?,
        val block: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(area)
            parcel.writeString(houseNumber)
            parcel.writeString(street)
            parcel.writeString(block)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<DisplayAddress> {
            override fun createFromParcel(parcel: Parcel): DisplayAddress {
                return DisplayAddress(parcel)
            }

            override fun newArray(size: Int): Array<DisplayAddress?> {
                return arrayOfNulls(size)
            }
        }
    }
}