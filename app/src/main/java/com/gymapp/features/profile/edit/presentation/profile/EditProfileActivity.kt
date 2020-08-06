package com.gymapp.features.profile.edit.presentation.profile

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.edit.domain.EditProfileViewModel
import com.gymapp.features.profile.edit.presentation.changename.ChangeCustomerNameActivity
import com.gymapp.features.profile.edit.presentation.image.ImageCropActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.DateHelper
import com.gymapp.helper.ImageHelper
import com.gymapp.helper.ui.InAppBannerNotification
import com.squareup.picasso.Picasso
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.bottomsheet_email_validation.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.textColor
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseActivity(R.layout.activity_edit_profile),
    DatePickerDialog.OnDateSetListener,
    EditProfileViewInterface {

    private lateinit var editProfileViewModel: EditProfileViewModel
    private var birthdayDate: Date? = null
    val REQUEST_CODE_PICK_IMAGE = 0x1
    val REQUEST_CODE_CROP_IMAGE = 0x2
    val REQUEST_CODE_CHANGE_CUSTOMER_NAME = 0x3
    val REQUEST_CODE_UPDATE_EMAIL = 0x4
    val REQUEST_CODE_UPDATE_PASSWORD = 0x5
    val REQUEST_CODE_UPDATE_PHONE = 0x6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(getString(R.string.edit_user_profile))

        editProfileViewModel.fetchData()

        firstNameEt.setOnClickListener {
            val intent = Intent(this, ChangeCustomerNameActivity::class.java)
            val args = Bundle()
            args.putBoolean(Constants.isFirstName, true)
            intent.putExtra(Constants.arguments, args)
            startActivityForResult(intent, REQUEST_CODE_CHANGE_CUSTOMER_NAME)
        }

        lastNameEt.setOnClickListener {
            val intent = Intent(this, ChangeCustomerNameActivity::class.java)
            val args = Bundle()
            args.putBoolean(Constants.isFirstName, false)
            intent.putExtra(Constants.arguments, args)
            startActivityForResult(intent, REQUEST_CODE_CHANGE_CUSTOMER_NAME)
        }

        phoneEt.setOnClickListener {
//            val intent = Intent(this, PhoneVerifyActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_UPDATE_PHONE)
        }

        birthdayEt.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -14)

            SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback(this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                .maxDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                .minDate(1960, 0, 1)
                .build()
                .show()
        }

        profileImageClickArea.setOnClickListener {
            val takePictureIntent = ImageHelper.getPickImageIntent(this)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                askPermission(Manifest.permission.CAMERA, acceptedblock = {
                    startActivityForResult(takePictureIntent, REQUEST_CODE_PICK_IMAGE)
                }).onDeclined { _ ->
                    Toast.makeText(this, "Camera permission declined", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        birthdayDate = calendar.time

        GlobalScope.launch {
            editProfileViewModel.saveChanges(birthdayDate)
        }

        birthdayEt.setText("$dayOfMonth/${monthOfYear + 1}/$year")
    }

    override fun setPasswordOnClickListener(email: String?) {
        passwordEt.setOnClickListener {
//            val intent = Intent(this, UpdatePasswordActivity::class.java)
            val args = Bundle()
            args.putString(Constants.email, email)
            intent.putExtra(Constants.arguments, args)
            startActivityForResult(intent, REQUEST_CODE_UPDATE_PASSWORD)
        }
    }


    override fun setupViewModel() {
        editProfileViewModel = getViewModel()
        editProfileViewModel.setListener(this)
    }

    override fun bindViewModelObservers() {
    }


    override fun setBirthday(birthday: String) {
        if (birthday.isNotEmpty()) {
            val spf = SimpleDateFormat(DateHelper.ISO8601_DATE, Locale.ENGLISH)
            birthdayDate = spf.parse(birthday)

            val calendar = Calendar.getInstance()
            calendar.time = birthdayDate
            birthdayEt.setText(
                "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(
                    Calendar.YEAR
                )}"
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                val imageUri = ImageHelper.getImageUriFromResult(this, resultCode, data)

                val intent = Intent(this, ImageCropActivity::class.java)
                intent.putExtra(ImageCropActivity.EXTRA_PROFILE_PIC_URI, imageUri)
                startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE)
            }
        } else if (requestCode == REQUEST_CODE_CROP_IMAGE) {
            editProfileViewModel.updateUserProfilePhoto()
        } else /*if (requestCode == REQUEST_CODE_CHANGE_CUSTOMER_NAME) */ {
            editProfileViewModel.fetchData()
        }
    }

    override fun setVerifiedPhoneStatus(isVerified: Boolean) {
        when (isVerified) {
            false -> {
                verifiedStatusPhoneTv.textColor =
                    resources.getColor(R.color.verified_red, this.theme)
                verifiedStatusPhoneTv.text = getString(R.string.user_profile_not_verified)
            }
            true -> {
                verifiedStatusPhoneTv.textColor =
                    resources.getColor(R.color.verified_green, this.theme)
                verifiedStatusPhoneTv.text = getString(R.string.user_profile_verified)
            }
        }
        verifiedStatusPhoneTv.visibility = View.VISIBLE
    }

    override fun changesSavedSuccesfully() {
        runOnUiThread {
            hideLoading()
        }
    }

    override fun setFirstName(firstName: String) {
        firstNameEt.setText(firstName)
    }

    override fun setLastName(lastName: String) {
        lastNameEt.setText(lastName)
    }

    override fun setPhone(phone: String?) {
        phoneEt.setText(phone)
    }

    override fun setEmail(email: String?) {
        emailEt.setText(email)
    }

    override fun setPhoto(photoUrl: String) {
        editProfileImage.visibility = View.VISIBLE
        Picasso.get()
            .load(photoUrl)
            .into(editProfileImage)
    }

    override fun setInitials(initials: String) {
        editProfileInitialsTextView.setText(initials)
        editProfileInitialsTextView.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showErrorBanner(textMessage: String?) {
        hideLoading()
        InAppBannerNotification.showErrorNotification(editProfileContainer, this, textMessage)
    }

}