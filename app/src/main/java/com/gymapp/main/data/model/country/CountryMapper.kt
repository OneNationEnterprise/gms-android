package com.gymapp.main.data.model.country

import com.apollographql.apollo.gym.fragment.CountryFields
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.main.data.model.user.DynamicAddressData

class CountryMapper : BaseDataMapperInterface<CountryFields, Country> {

    override fun mapToDto(countryFields: CountryFields): Country {
        return Country(
            countryId = countryFields.id,
            dialCode = countryFields.dialCode ?: "",
            isoCode = countryFields.isoCode ?: "",
            name = countryFields.name,
            flagPhoto = countryFields.flagPhoto ?: "",
            addresses = mapAddress(countryFields.addressFields)
        )
    }

    override fun mapToDtoList(input: List<CountryFields?>?): List<Country> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }

    fun mapAddress(addressFields: List<CountryFields.AddressField>?): List<DynamicAddressData> {

        val addresses = ArrayList<DynamicAddressData>()

        if (addressFields.isNullOrEmpty()) return addresses

        for (field in addressFields) {
            addresses.add(
                DynamicAddressData(
                    id = field.id,
                    name = field.title,
                    isRequired = field.isRequired,
                    value = ""
                )
            )
        }

        return addresses
    }
}