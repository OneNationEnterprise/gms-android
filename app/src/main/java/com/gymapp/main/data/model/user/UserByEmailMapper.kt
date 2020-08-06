package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.CustomerByEmailQuery
import com.gymapp.base.data.BaseDataMapperInterface

class UserByEmailMapper :
    BaseDataMapperInterface<CustomerByEmailQuery.CustomerByEmail, User> {

    override fun mapToDto(customer: CustomerByEmailQuery.CustomerByEmail): User {
        return User(
            customer.fragments.customerFields.id,
            customer.fragments.customerFields.firstName,
            customer.fragments.customerFields.lastName,
            customer.fragments.customerFields.fullName,
            customer.fragments.customerFields.email,
            customer.fragments.customerFields.contactNumber,
            mapUserCountry(customer.fragments.customerFields.country.id),
            customer.fragments.customerFields.photo,
            customer.fragments.customerFields.dob.toString()
        )

    }

    override fun mapToDtoList(input: List<CustomerByEmailQuery.CustomerByEmail?>?): List<User> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }

    fun mapUserCountry(countryId: String): UserCountry {
        return UserCountry(countryId)
    }
}