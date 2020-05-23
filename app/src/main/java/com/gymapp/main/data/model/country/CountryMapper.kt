package com.gymapp.main.data.model.country

import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.CustomerByEmailQuery
import com.gymapp.base.data.BaseDataMapperInterface

class CountryMapper : BaseDataMapperInterface<CountriesQuery.List, Country> {

    override fun mapToDto(input: CountriesQuery.List): Country {
        return Country(
            id = input.fragments.countryFields.id,
            dialCode = input.fragments.countryFields.dialCode ?: "",
            isoCode = input.fragments.countryFields.isoCode ?: "",
            name = input.fragments.countryFields.name,
            flagPhoto = input.fragments.countryFields.flagPhoto ?: ""
        )
    }

    override fun mapToDtoList(input: List<CountriesQuery.List>): List<Country> {
        return input.map {
            mapToDto(it)
        }
    }
}