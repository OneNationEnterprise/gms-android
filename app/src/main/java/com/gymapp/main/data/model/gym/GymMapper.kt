package com.gymapp.main.data.model.gym

import com.apollographql.apollo.gym.GymsInRadiusQuery
import com.apollographql.apollo.gym.fragment.GymFields
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.main.data.model.brand.Brand

class GymMapper : BaseDataMapperInterface<GymsInRadiusQuery.List, Gym> {

    override fun mapToDto(input: GymsInRadiusQuery.List): Gym {

        val gymFields = input.fragments.gymFields

        return Gym(
            gymId = gymFields.id,
            name = gymFields.name,
            address = mapGymAddress(gymFields.address),
            images = gymFields.images,
            brand = mapBrand(gymFields.brand),
            distance = gymFields.distance,
            phone = gymFields.phone,
            description = gymFields.description,
            opening = mapOpening(gymFields.opening),
            amenityList = mapAmenity(gymFields.amenityList)

        )
    }

    override fun mapToDtoList(input: List<GymsInRadiusQuery.List?>?): List<Gym> {

        if (input.isNullOrEmpty()) return ArrayList()

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
            geoLocation = mapGeolocation(address.geoLocation)
        )
    }


    private fun mapGeolocation(geolocation: GymFields.GeoLocation): Geolocation {
        return Geolocation(
            coordinates = geolocation.coordinates
        )
    }

    private fun mapOpening(opening: GymFields.Opening?): Opening {
        return Opening(
            OperatingTime(
                begin = opening?.operatingTime?.begin.toString(),
                end = opening?.operatingTime?.end.toString()
            )
        )
    }

    private fun mapAmenity(amenityList: List<GymFields.AmenityList>?): List<Amenity>? {
        return amenityList?.map {
            Amenity(it.icon, it.name, it.status)
        }
    }
}