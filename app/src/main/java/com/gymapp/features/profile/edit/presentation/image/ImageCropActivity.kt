package com.gymapp.features.profile.edit.presentation.image

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.edit.domain.ImageCropViewModel
import com.gymapp.helper.ImageHelper
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_image_crop.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.io.IOException

class ImageCropActivity : BaseActivity(R.layout.activity_image_crop), ImageCropView {

    lateinit var imageCropViewModel: ImageCropViewModel
    lateinit var activity: BaseActivity

    companion object {
        val EXTRA_PROFILE_PIC_URI = "profile_pic_uri"
    }

    private val MAX_IMAGE_DIMENSION = 1024

    override fun bindViewModelObservers() {}

    override fun setupViewModel() {
        imageCropViewModel = getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        activity = this

        imageCropViewModel.setListener(this)

        val bitmapUri = intent.getParcelableExtra<Uri?>(EXTRA_PROFILE_PIC_URI)

        if (bitmapUri != null) {
            cropImageView.setImageUriAsync(bitmapUri)
        }

        saveBtn.setOnClickListener {
            saveOutputPicture();
        }

        cancelBtn.setOnClickListener {
            finish()
        }
    }


    private fun saveOutputPicture() {
        cropImageView.setOnCropImageCompleteListener { _, result ->

            try {
                val uri = ImageHelper.saveProfilePic(this, "customerUserProfilePic", result.bitmap)
                if (uri != null) {
                    imageCropViewModel.cloudinaryPicUpload(uri)
                }
            } catch (e: IOException) {
                InAppBannerNotification.showErrorNotification(
                    imageCropContainer,
                    this,
                    getString(R.string.error_unknown)
                )
            }
        }
        imageCropProgressBar.visibility = View.VISIBLE
        cropImageView.getCroppedImageAsync(MAX_IMAGE_DIMENSION, MAX_IMAGE_DIMENSION)
    }

    override fun errorUpdatingPicture(error: String) {
        CoroutineScope(Dispatchers.Main).launch {
            imageCropProgressBar.visibility = View.GONE
            InAppBannerNotification.showErrorNotification(
                imageCropContainer,
                activity,
                getString(R.string.error_unknown)
            )
        }
    }

    override fun pictureUploadedSuccessfully() {

        CoroutineScope(Dispatchers.Main).launch {
            imageCropProgressBar.visibility = View.GONE
            setResult(RESULT_OK)
            /**
             * post profile photo change to [HomeActivity.kt]
             */
//        EventBus.getDefault().post(EventBusMessage.HasChangedProfilePhoto(true))
            finish()
        }
    }

}
