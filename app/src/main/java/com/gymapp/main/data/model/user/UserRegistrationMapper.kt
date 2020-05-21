package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.RegisterUserMutation
import com.gymapp.base.data.BaseDataMapperInterface

class UserRegistrationMapper :
    BaseDataMapperInterface<RegisterUserMutation.Customer, User> {

    override fun mapToDto(customer: RegisterUserMutation.Customer): User {
        return User(
            customer.id,
            customer.fullName,
            customer.email,
            customer.contactNumber,
            customer.countryId,
            customer.photo
        )

    }

    override fun mapToDtoList(input: List<RegisterUserMutation.Customer>): List<User> {
        return input.map {
            mapToDto(it)
        }
    }
}