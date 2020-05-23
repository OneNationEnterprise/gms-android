package com.gymapp.features.onboarding.auth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.model.user.User

interface AuthRepositoryInterface : BaseRepositoryInterface {

    /**
     * returns error message (null if successful)
     */
    suspend fun registerUser(input: RegisterCustomerInput): String?
}