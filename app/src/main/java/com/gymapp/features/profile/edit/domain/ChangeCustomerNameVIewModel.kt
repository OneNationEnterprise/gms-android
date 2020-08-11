package com.gymapp.features.profile.edit.domain

import android.annotation.SuppressLint
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.SaveCustomerInput
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.features.profile.edit.presentation.changename.ChangeCustomerNameView
import com.gymapp.helper.DateHelper
import com.gymapp.helper.extensions.isValidName
import java.text.SimpleDateFormat
import java.util.*

class ChangeCustomerNameVIewModel(val userRepositoryInterface: UserRepositoryInterface) :
    BaseViewModel() {


    lateinit var listener: ChangeCustomerNameView


    fun fetchData(isFirstName: Boolean) {
        val customer = userRepositoryInterface.getCurrentUser() ?: return
        if (isFirstName) {
            listener.setValue(customer.firstName)
        } else {
            listener.setValue(customer.lastName)
        }
    }

    @SuppressLint("CheckResult")
    suspend fun saveFirstName(firstName: String) {

        if (!firstName.isValidName()) {
            listener?.showNotValidFirstName()
            return
        }

        listener?.showLoading()

        val profileDetails = userRepositoryInterface.getCurrentUser() ?: return

        var birthdayDate: Date? = null
        val spf = SimpleDateFormat(DateHelper.ISO8601_DATE, Locale.ENGLISH)
        if (profileDetails.dob != null && profileDetails.dob != "null") {
            birthdayDate = spf.parse(profileDetails.dob.toString())
        }

        val error = userRepositoryInterface.saveCustomer(
            SaveCustomerInput(
                firstName,
                profileDetails.lastName,
                profileDetails.email,
                profileDetails.contactNumber!!,
                Input.fromNullable(null),
                Input.fromNullable(profileDetails.photo),
                Input.fromNullable(birthdayDate)
            )
        )


        if (error.isNullOrEmpty()) {
            listener.changesSavedSuccesfully()
        } else {
            listener.showErrorBanner(error)
        }
    }

    @SuppressLint("CheckResult")
    suspend fun saveLastName(lastName: String) {

        if (!lastName.isValidName()) {
            listener?.showNotValidLastName()
            return
        }

        listener?.showLoading()

        val profileDetails = userRepositoryInterface.getCurrentUser() ?: return

        var birthdayDate: Date? = null
        val spf = SimpleDateFormat(DateHelper.ISO8601_DATE, Locale.ENGLISH)
        if (profileDetails.dob != null && profileDetails.dob != "null") {
            birthdayDate = spf.parse(profileDetails.dob.toString())
        }


        val error = userRepositoryInterface.saveCustomer(
            SaveCustomerInput(
                profileDetails.firstName,
                lastName,
                profileDetails.email,
                profileDetails.contactNumber!!,
                Input.fromNullable(null),
                Input.fromNullable(profileDetails.photo),
                Input.fromNullable(birthdayDate)
            )
        )


        if (error.isNullOrEmpty()) {
            listener.changesSavedSuccesfully()
        } else {
            listener.showErrorBanner(error)
        }
    }
}