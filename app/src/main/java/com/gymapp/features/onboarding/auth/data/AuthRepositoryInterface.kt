package com.gymapp.features.onboarding.auth.data

import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.apollographql.apollo.gym.type.SaveCustomerInput
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.model.user.User

interface AuthRepositoryInterface : BaseRepositoryInterface {

    /**
     * returns error message (null if successful)
     */
    suspend fun registerUser(input: RegisterCustomerInput): String?

    /**
     * save user details from server to room_db
     * returns error message (null if successful)
     */
    suspend fun saveUserDetailsByEmail(email: String): String?

    /**
     * returns current session user from Room
     */
    fun getCurrentUser(): User?


    fun invalidateUserDataOnLogout()

    suspend fun saveCustomer(input: SaveCustomerInput) : String?

}