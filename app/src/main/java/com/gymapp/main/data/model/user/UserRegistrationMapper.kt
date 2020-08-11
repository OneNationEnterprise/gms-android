package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.RegisterUserMutation
import com.apollographql.apollo.gym.fragment.CustomerFields
import com.gymapp.base.data.BaseDataMapperInterface

class UserRegistrationMapper :
    BaseDataMapperInterface<RegisterUserMutation.Customer, User> {

    override fun mapToDto(input: RegisterUserMutation.Customer): User {
        return User(
            input.fragments.customerFields.id,
            input.fragments.customerFields.firstName,
            input.fragments.customerFields.lastName,
            input.fragments.customerFields.fullName,
            input.fragments.customerFields.email,
            input.fragments.customerFields.contactNumber,
            mapUserCountry(input.fragments.customerFields.country.id),
            input.fragments.customerFields.photo,
            input.fragments.customerFields.dob.toString(),
            mapUserAddress(input.fragments.customerFields.addresses)
        )
    }

    override fun mapToDtoList(input: List<RegisterUserMutation.Customer?>?): List<User> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }

    fun mapUserCountry(countryId: String): UserCountry {
        return UserCountry(countryId)
    }

    fun mapUserAddress(addressesList: List<CustomerFields.Address>?): List<AddressUser> {

        val addresses = ArrayList<AddressUser>()
        val dynamicFields = ArrayList<DynamicAddressData>()

        if (addressesList == null) addresses

        for (address in addressesList!!) {

            dynamicFields.clear()

            if (address.dynamicFullData != null) {
                for (dynamicField in address.dynamicFullData) {
                    val dynamicField = DynamicAddressData(
                        id = dynamicField.id,
                        isRequired = dynamicField.isRequired,
                        name = dynamicField.title,
                        value = dynamicField.value
                    )
                    dynamicFields.add(dynamicField)
                }
            }

            addresses.add(
                AddressUser(
                    id = address.id,
                    dynamicData = dynamicFields,
                    countryId = address.country.id,
                    geolocation = Geolocation(address.geoLocation?.coordinates)
                )
            )
        }

        return addresses
    }
}