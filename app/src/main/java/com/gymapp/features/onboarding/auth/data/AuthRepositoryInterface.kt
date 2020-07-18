package com.gymapp.features.onboarding.auth.data

import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.gymapp.base.data.BaseRepositoryInterface

interface AuthRepositoryInterface : BaseRepositoryInterface {

    /**
     * returns error message (null if successful)
     */
    suspend fun registerUser(input: RegisterCustomerInput): String?
}