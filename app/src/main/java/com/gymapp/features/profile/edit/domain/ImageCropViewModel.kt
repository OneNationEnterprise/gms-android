package com.gymapp.features.profile.edit.domain

import android.annotation.SuppressLint
import android.net.Uri
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.CustomerPhotoInput
import com.apollographql.apollo.gym.type.SaveCustomerInput
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.features.profile.edit.presentation.image.ImageCropView
import com.gymapp.helper.Constants
import com.gymapp.main.network.ApiManagerInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ImageCropViewModel(
    val userRepositoryInterface: UserRepositoryInterface,
    val apiManagerInterface: ApiManagerInterface
) : BaseViewModel() {

    lateinit var imageCropView: ImageCropView


    fun setListener(imageCropView: ImageCropView) {
        this.imageCropView = imageCropView
    }

    fun cloudinaryPicUpload(uri: Uri) {

        val paramsToSign = HashMap<String, Any>()
        paramsToSign["folder"] = "user-dp"
        paramsToSign["overwrite"] = true
        paramsToSign["public_id"] = userRepositoryInterface.getCurrentUser()!!.id
        paramsToSign["tags"] = "android,customer,display-photo,${Constants.BASE_SERVER_URL}"
        paramsToSign["timestamp"] = Date().time / 1000
//        paramsToSign["upload_preset"] = "square_image"

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

                    val profileDetails = userRepositoryInterface.getCurrentUser() ?: return


                    GlobalScope.launch {
                        val customerData = apiManagerInterface.saveCustomerPhotoAsync(
                            CustomerPhotoInput(
                                resultData["secure_url"] as String
                            )
                        ).await()

                        if (!customerData.errors.isNullOrEmpty()) {
                            imageCropView.errorUpdatingPicture("Error updating picture!")
                            return@launch
                        }

                        val error = userRepositoryInterface.saveUserDetails()

                        if (!error.isNullOrEmpty()) {
                            imageCropView.errorUpdatingPicture(error)
                            return@launch
                        }

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