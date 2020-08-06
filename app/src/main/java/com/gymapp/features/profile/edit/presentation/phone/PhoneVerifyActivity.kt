package com.gymapp.features.profile.edit.presentation.phone

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.edit.domain.PhoneVerifyViewModel
import kotlinx.android.synthetic.main.verify_phone.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import render.animations.Render

//class PhoneVerifyActivity : BaseActivity(R.layout.activity_phone_verify), PhoneVerifyView {
//
//    var phoneVerifyViewModel: PhoneVerifyViewModel = getViewModel()
//    lateinit var render: Render
//    private var firebaseUser: FirebaseUser? = null
//    var otpResentCounter = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        phoneVerifyViewModel.phoneVerifyView = this
//
//        setTitle(getString(R.string.edit_user_profile))
//
//
//        switchView.visibility = View.GONE
//        signUpPhoneDescriptionTv.visibility = View.GONE
//
//        render = Render(this)
//        render.setDuration(300)
//
//        initPhoneView()
//
//        phoneVerifyViewModel.setPhonePrefix()
//        phoneVerifyViewModel.setPhone()
//
//        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
//
//        val isVerified = !currentUser.phoneNumber.isNullOrEmpty()
//
//        if (isVerified) {
//            continueTv.text = getString(R.string.user_profile_update_phone)
//        } else {
//            continueTv.text = getString(R.string.user_profile_verify_phone)
//        }
//    }
//
//
//    override fun setupViewModel() {
//    }
//
//    override fun bindViewModelObservers() {
//    }
//}