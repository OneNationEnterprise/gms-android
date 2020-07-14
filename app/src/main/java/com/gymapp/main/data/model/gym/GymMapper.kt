package com.gymapp.main.data.model.gym

import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.GymsInRadiusQuery
import com.apollographql.apollo.gym.fragment.GymFields
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.main.data.model.brand.Brand
import com.gymapp.main.data.model.country.CountryMapper

class GymMapper : BaseDataMapperInterface<GymsInRadiusQuery.List, Gym> {

    override fun mapToDto(input: GymsInRadiusQuery.List): Gym {

        val gymFields = input.fragments.gymFields

        return Gym(
            id = gymFields.id,
            name = gymFields.name,
            address = mapGymAddress(gymFields.address),
            brand = mapBrand(gymFields.brand)
        )
    }

    override fun mapToDtoList(input: List<GymsInRadiusQuery.List>): List<Gym> {
        return input.map {
            mapToDto(it)
        }
    }

    private fun mapBrand(brand: GymFields.Brand): Brand {

        val brandFields = brand.fragments.brandFields

        return Brand(
            id = brandFields.id,
            name = brandFields.name,
            phone = brandFields.phone,
            gymCount = brandFields.gymCount
        )
    }

    private fun mapGymAddress(address: GymFields.Address): GymAddress {

        val countryMapper = CountryMapper()

        return GymAddress(
            id = address.id,
//            country = countryMapper.mapToDto(address.country.fragments.countryFields),
            unitNumber = address.unitNumber ?: ""
        )

    }

}