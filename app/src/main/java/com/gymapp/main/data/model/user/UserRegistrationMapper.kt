package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.RegisterUserMutation
import com.gymapp.base.data.BaseDataMapperInterface

class UserRegistrationMapper :
    BaseDataMapperInterface<RegisterUserMutation.Customer, User> {

    override fun mapToDto(input: RegisterUserMutation.Customer): User {
        return User(
            input.fragments.customerFields.id,
            input.fragments.customerFields.fullName,
            input.fragments.customerFields.email,
            input.fragments.customerFields.contactNumber,
            input.fragments.customerFields.countryId,
            input.fragments.customerFields.photo
        )

    }

    override fun mapToDtoList(input: List<RegisterUserMutation.Customer?>): List<User> {
        return input.map {
            mapToDto(it!!)
        }
    }
}