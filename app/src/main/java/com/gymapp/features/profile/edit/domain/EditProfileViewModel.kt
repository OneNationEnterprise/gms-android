package com.gymapp.features.profile.edit.domain

import android.annotation.SuppressLint
import android.text.TextUtils
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.SaveCustomerInput
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.features.profile.edit.presentation.profile.EditProfileViewInterface
import com.gymapp.main.data.model.user.User
import java.util.*

class EditProfileViewModel(private val userRepositoryInterface: UserRepositoryInterface) :
    BaseViewModel() {

    lateinit var editProfileInterface: EditProfileViewInterface
    lateinit var profileDetails: User

    fun setListener(editProfileInterface: EditProfileViewInterface) {
        this.editProfileInterface = editProfileInterface
    }

    fun fetchData() {
        profileDetails = userRepositoryInterface.getCurrentUser()!!

        editProfileInterface.setFirstName(profileDetails.firstName)
        editProfileInterface.setLastName(profileDetails.lastName)
        editProfileInterface.setPhone(profileDetails.contactNumber)
        editProfileInterface.setEmail(profileDetails.email)
        if (profileDetails.dob != null) {
            editProfileInterface.setBirthday(profileDetails.dob.toString())
        }
        if (!TextUtils.isEmpty(profileDetails.photo)) {
            editProfileInterface.setPhoto(profileDetails.photo!!)
        } else {
            editProfileInterface.setInitials(profileDetails.fullName)
        }

        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        currentUser.reload()

        editProfileInterface.setPasswordOnClickListener(profileDetails.email)

        editProfileInterface.setVerifiedPhoneStatus(!currentUser.phoneNumber.isNullOrEmpty())
    }


    fun updateUserProfilePhoto() {

        if (!TextUtils.isEmpty(profileDetails.photo)) {
            profileDetails.photo.let { editProfileInterface.setPhoto(it!!) }
        } else {
            editProfileInterface.setInitials(profileDetails.fullName)
        }
    }

    @SuppressLint("CheckResult")
    suspend fun saveChanges(birthday: Date?) {

        editProfileInterface.showLoading()

        if (profileDetails != null) {

            val error = userRepositoryInterface.saveCustomer(
                SaveCustomerInput(
                    profileDetails.firstName,
                    profileDetails.lastName,
                    profileDetails.email,
                    profileDetails.contactNumber!!,
                    Input.fromNullable(null),
                    Input.fromNullable(profileDetails.photo),
                    Input.fromNullable(birthday)
                )
            )

            if (error.isNullOrEmpty()) {
                editProfileInterface.changesSavedSuccesfully()
            } else {
                editProfileInterface.showErrorBanner(error)
            }

        } else {
            editProfileInterface.showErrorBanner("Error retriving user profile details")
        }
    }

}