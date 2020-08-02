package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.CustomerByEmailQuery
import com.gymapp.base.data.BaseDataMapperInterface

class UserByEmailMapper :
    BaseDataMapperInterface<CustomerByEmailQuery.CustomerByEmail, User> {

    override fun mapToDto(customer: CustomerByEmailQuery.CustomerByEmail): User {
        return User(
            customer.fragments.customerFields.id,
            customer.fragments.customerFields.fullName,
            customer.fragments.customerFields.email,
            customer.fragments.customerFields.contactNumber,
            customer.fragments.customerFields.countryId,
            customer.fragments.customerFields.photo
        )

    }

    override fun mapToDtoList(input: List<CustomerByEmailQuery.CustomerByEmail?>?): List<User> {

        if (input.isNullOrEmpty()) return emptyList()

        return input.map {
            mapToDto(it!!)
        }
    }
}