package com.gymapp.features.onboarding.auth.data

import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.apollographql.apollo.gym.type.SaveCustomerInput
import com.gymapp.base.data.BaseRepository
import com.gymapp.main.data.model.user.User
import com.gymapp.main.data.model.user.UserByEmailMapper
import com.gymapp.main.data.model.user.UserBySaveCustomerMapper
//import com.gymapp.main.data.db.GymDao
import com.gymapp.main.data.model.user.UserRegistrationMapper
import com.gymapp.main.network.ApiManagerInterface

class AuthRepository(private val apiManager: ApiManagerInterface/*, private val gymDao: GymDao*/) :
    BaseRepository(apiManager/*, gymDao*/), AuthRepositoryInterface {

    private val userMapper = UserByEmailMapper()
    private var user: User? = null
    private val userRegistrationMapper = UserRegistrationMapper()

    override suspend fun registerUser(input: RegisterCustomerInput): String? {

        val apiResponse = apiManager.registerUserAsync(input).await()

        val errorMessage = apiResponse.data?.registerCustomer?.errorMessage

        if (errorMessage != null) {
            return errorMessage
        }

        val customer =
            apiResponse.data?.registerCustomer?.customer ?: return "Null customer response"

        user = userRegistrationMapper.mapToDto(customer)

//        gymDao.insertUser(user)

        return null
    }

    override suspend fun saveUserDetailsByEmail(email: String): String? {

        val userDetailsResponse = apiManager.getUserDetailsByEmailAsync(email).await()

        if (userDetailsResponse.errors != null && userDetailsResponse.errors!!.isNotEmpty()
            || userDetailsResponse.data == null
            || (userDetailsResponse.data!!.customerByEmail == null)
        ) {
            return "Error on getting user details"
        }

//        gymDao.insertUser(userMapper.mapToDto(userDetailsResponse.data!!.customerByEmail!!))

        user = userMapper.mapToDto(userDetailsResponse.data!!.customerByEmail!!)
        return null
    }

    override fun getCurrentUser(): User? {
        return user
    }

    override fun invalidateUserDataOnLogout() {
        user = null
    }

    override suspend fun saveCustomer(input: SaveCustomerInput): String? {
        val apiResponse = apiManager.saveCustomerAsync(input).await()

        if (!apiResponse.data?.saveCustomer?.errorMessage.isNullOrEmpty()) return apiResponse.data?.saveCustomer?.errorMessage


        val user1 = apiResponse.data!!.saveCustomer!!.customer

        val mapper = UserBySaveCustomerMapper()

        user = mapper.mapToDto(user1!!)

        return null
    }
}

