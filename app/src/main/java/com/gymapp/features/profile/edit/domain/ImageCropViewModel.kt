package com.gymapp.features.profile.edit.domain

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.SaveCustomerInput
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.gymapp.BuildConfig
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.features.profile.edit.presentation.image.ImageCropView
import com.gymapp.helper.Constants
import com.gymapp.helper.DateHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ImageCropViewModel(val authRepositoryInterface: AuthRepositoryInterface) : BaseViewModel() {

    lateinit var imageCropView: ImageCropView


    fun setListener(imageCropView: ImageCropView) {
        this.imageCropView = imageCropView
    }

    fun cloudinaryPicUpload(uri: Uri) {

        val paramsToSign = HashMap<String, Any>()
        paramsToSign["folder"] = "user-dp"
        paramsToSign["overwrite"] = true
        paramsToSign["public_id"] = authRepositoryInterface.getCurrentUser()!!.id
        paramsToSign["tags"] = "android,customer,display-photo,${Constants.BASE_SERVER_URL}"
        paramsToSign["timestamp"] = Date().time / 1000
        paramsToSign["upload_preset"] = "square_image"

        MediaManager.get().upload(uri)
            .options(paramsToSign)
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) {
//                    Timber.d("onStart_request_id:$requestId")
                }

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
//                    Timber.d("onProgress_request_id:$requestId")
                }

                @SuppressLint("CheckResult")
                override fun onSuccess(requestId: String, resultData: Map<*, *>) {

                    val customer = authRepositoryInterface.getCurrentUser() ?: return

                    var birthdaDate: Date? = null

                    try {
                        birthdaDate = SimpleDateFormat(
                            DateHelper.ISO8601_DATE,
                            Locale.ENGLISH
                        ).parse(customer.dob.toString())
                    } catch (e: ParseException) {
                    }


                    val profileDetails = authRepositoryInterface.getCurrentUser() ?: return


                    GlobalScope.launch {
                        val error = authRepositoryInterface.saveCustomer(
                            SaveCustomerInput(
                                profileDetails.firstName,
                                profileDetails.lastName,
                                profileDetails.email,
                                profileDetails.contactNumber!!,
                                Input.fromNullable(null),
                                Input.fromNullable(resultData["secure_url"] as String),
                                Input.fromNullable(profileDetails.dob)
                            )
                        )

                        if (!error.isNullOrEmpty()) {
                            imageCropView.errorUpdatingPicture(error)
                            return@launch
                        }

                        authRepositoryInterface.saveUserDetailsByEmail(customer.email)

                        imageCropView.pictureUploadedSuccessfully()
                    }

                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    imageCropView.errorUpdatingPicture(error.description)
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {
                    imageCropView.errorUpdatingPicture(error.description)
                }
            }).dispatch()

    }
}