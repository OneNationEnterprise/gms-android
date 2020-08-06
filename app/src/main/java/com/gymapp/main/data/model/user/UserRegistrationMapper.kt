package com.gymapp.main.data.model.user

import com.apollographql.apollo.gym.RegisterUserMutation
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
            input.fragments.customerFields.dob.toString()
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
}