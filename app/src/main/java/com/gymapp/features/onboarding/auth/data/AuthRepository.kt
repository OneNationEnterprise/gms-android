package com.gymapp.features.onboarding.auth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.gymapp.base.data.BaseRepository
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.data.model.user.User
import com.gymapp.main.data.model.user.UserRegistrationMapper
import com.gymapp.main.network.ApiManagerInterface

class AuthRepository(private val apiManager: ApiManagerInterface, private val gymDao: GymDao) :
    BaseRepository(apiManager, gymDao), AuthRepositoryInterface {

    private val userRegistrationMapper = UserRegistrationMapper()

    override suspend fun registerUser(input: RegisterCustomerInput): String? {

        val apiResponse = apiManager.registerUserAsync(input).await()

        val errorMessage = apiResponse.data?.registerCustomer?.errorMessage

        if (errorMessage != null) {
            return errorMessage
        }

        val customer =
            apiResponse.data?.registerCustomer?.customer ?: return "Null customer response"

        val user = userRegistrationMapper.mapToDto(customer)

        gymDao.insertUser(user)

        return null
    }
}

