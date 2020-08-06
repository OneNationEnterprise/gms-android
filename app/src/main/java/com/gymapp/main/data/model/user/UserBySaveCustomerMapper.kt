package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.CustomerByEmailQuery
import com.apollographql.apollo.gym.SaveCustomerMutation
import com.gymapp.base.data.BaseDataMapperInterface

class UserBySaveCustomerMapper :
    BaseDataMapperInterface<SaveCustomerMutation.Customer, User> {

    override fun mapToDto(customer: SaveCustomerMutation.Customer): User {
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

    override fun mapToDtoList(input: List<SaveCustomerMutation.Customer?>?): List<User> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }

    fun mapUserCountry(countryId: String): UserCountry {
        return UserCountry(countryId)
    }
}