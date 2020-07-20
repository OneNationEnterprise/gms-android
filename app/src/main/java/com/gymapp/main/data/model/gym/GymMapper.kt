package com.gymapp.main.data.model.gym

import com.apollographql.apollo.gym.GymsInRadiusQuery
import com.apollographql.apollo.gym.fragment.GymFields
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.main.data.model.brand.Brand
import com.gymapp.main.data.model.country.CountryMapper

class GymMapper : BaseDataMapperInterface<GymsInRadiusQuery.List, Gym> {

    override fun mapToDto(input: GymsInRadiusQuery.List): Gym {

        val gymFields = input.fragments.gymFields

        return Gym(
            gymId = gymFields.id,
            name = gymFields.name,
            address = mapGymAddress(gymFields.address),
            images = gymFields.images,
            brand = mapBrand(gymFields.brand)
        )
    }

    override fun mapToDtoList(input: List<GymsInRadiusQuery.List?>): List<Gym> {
        return input.map {
            mapToDto(it!!)
        }
    }

    private fun mapBrand(brand: GymFields.Brand): Brand {

        val brandFields = brand.fragments.brandFields

        return Brand(
            brandId = brandFields.id,
            name = brandFields.name,
            phone = brandFields.phone,
            gymCount = brandFields.gymCount,
            logo = brandFields.logo
        )
    }

    private fun mapGymAddress(address: GymFields.Address): GymAddress {
        return GymAddress(
            gymAddressId = address.id,
            countryId = address.country.id,
            unitNumber = address.unitNumber ?: "",
            longitude = address.longitude,
            latitude = address.latitude
        )

    }

}