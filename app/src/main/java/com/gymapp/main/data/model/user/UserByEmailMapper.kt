package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.CustomerByEmailQuery
import com.apollographql.apollo.gym.fragment.CustomerFields
import com.gymapp.base.data.BaseDataMapperInterface

class UserByEmailMapper :
    BaseDataMapperInterface<CustomerByEmailQuery.CustomerByAuth, User> {

    override fun mapToDto(customer: CustomerByEmailQuery.CustomerByAuth): User {
        return User(
            customer.fragments.customerFields.id,
            customer.fragments.customerFields.firstName,
            customer.fragments.customerFields.lastName,
            customer.fragments.customerFields.fullName,
            customer.fragments.customerFields.email,
            customer.fragments.customerFields.contactNumber,
            mapUserCountry(customer.fragments.customerFields.country.id),
            customer.fragments.customerFields.photo,
            customer.fragments.customerFields.dob.toString(),
            mapUserAddress(customer.fragments.customerFields.addresses)
        )
    }

    override fun mapToDtoList(input: List<CustomerByEmailQuery.CustomerByAuth?>?): List<User> {

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