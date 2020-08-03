package com.gymapp.main.data.model.country

import com.apollographql.apollo.gym.fragment.CountryFields
import com.gymapp.base.data.BaseDataMapperInterface

class CountryMapper : BaseDataMapperInterface<CountryFields, Country> {

    override fun mapToDto(countryFields: CountryFields): Country {
        return Country(
            countryId = countryFields.id,
            dialCode = countryFields.dialCode ?: "",
            isoCode = countryFields.isoCode ?: "",
            name = countryFields.name,
            flagPhoto = countryFields.flagPhoto ?: ""
        )
    }

    override fun mapToDtoList(input: List<CountryFields?>?): List<Country> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }
}