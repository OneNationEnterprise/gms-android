package com.gymapp.features.onboarding.auth.presentation

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseDialogFragment
import com.gymapp.features.homepage.HomepageActivity
import com.gymapp.features.onboarding.auth.domain.AuthViewModel
import com.gymapp.helper.modal.phoneprefix.PhonePrefixModalBottomsheet
import com.gymapp.helper.modal.phoneprefix.PhonePrefixSelectedListener
import com.gymapp.main.data.model.country.Country
import kotlinx.android.synthetic.main.dialog_auth.*
import kotlinx.android.synthetic.main.register_flow_input_otp.*
import kotlinx.android.synthetic.main.register_flow_input_phone.*
import kotlinx.android.synthetic.main.view_phone_input.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import render.animations.Render
import render.animations.Slide

open abstract class BaseAuthDialogFragment() :
    BaseDialogFragment(R.layout.dialog_auth), PhonePrefixSelectedListener {

    lateinit var render: Render
    lateinit var authViewModel: AuthViewModel

    override fun bindView(savedInstanceState: Bundle?) {

        render = Render(context!!)
        render.setDuration(300)

        closeIv.setOnClickListener {
            dismiss()
        }

        phonePrefixHolder.setOnClickListener {
            showModalBottomSheet(this)
        }

        setupViewModelAndBindBaseObservers()

        continueWithOtpBtn.setOnClickListener {
            authViewModel.validatePhoneNumber(phoneInputView.getPhoneNumber(), baseActivity)
        }

        otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {}

            override fun onOTPComplete(otp: String) {
                authViewModel.validateOtp(otp)
            }
        }

        bindAdditionalView()
    }

    private fun setupViewModelAndBindBaseObservers() {
        authViewModel = getViewModel()

        authViewModel.getAvailableCountries()?.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                dialCodeSelected(it[0])
            }
        })

        authViewModel.showOtpView.observe(viewLifecycleOwner, Observer {
            if (it) {
                render.setAnimation(Slide().OutLeft(authInputPhoneLayout))
                render.start()
                render.setAnimation(Slide().InRight(authInputOtpLayout))
                authInputOtpLayout.visibility = View.VISIBLE
                render.start()
                authInputPhoneLayout.visibility = View.GONE

                otpView.requestFocusOTP()
            } else {
                render.setAnimation(Slide().OutLeft(authInputOtpLayout))
                render.start()
                render.setAnimation(Slide().InRight(authInputPhoneLayout))
                authInputPhoneLayout.visibility = View.VISIBLE
                render.start()
                authInputOtpLayout.visibility = View.GONE
            }
        })

        authViewModel.showPhoneNumberError.observe(viewLifecycleOwner, Observer {
            if (it) {
                phoneInputView.setError(View.VISIBLE)
            } else {
                phoneInputView.setError(View.GONE)
            }
        })

        authViewModel.otpFailedVerificationMessage.observe(viewLifecycleOwner, Observer {
            invalidOTPCode.visibility = View.VISIBLE
            invalidOTPCode.text = it
        })

        authViewModel.authErrorMessage.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                //registration successful, open next page
                startActivity(Intent(activity, HomepageActivity::class.java))
            } else {
                //show error message
            }
        })

        authViewModel.enableResendOtpButton.observe(viewLifecycleOwner, Observer {
            if (it) {
                otpResendTv.setOnClickListener {
                    authViewModel.resendOtpVerificationTimer()
                    authViewModel.resendOtp(baseActivity)
                }
                otpResendTv.setTextColor(ContextCompat.getColor(baseActivity, R.color.red001))
            } else {
                otpResendTv.setOnClickListener {}
                otpResendTv.setTextColor(R.color.blue001)
            }
        })

        bindViewModelObservers()
    }

    private fun showModalBottomSheet(listener: PhonePrefixSelectedListener) {
        authViewModel.getAvailableCountries()?.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {

                val modalBottomSheet = PhonePrefixModalBottomsheet(it, listener)

                baseActivity.supportFragmentManager.beginTransaction()

                modalBottomSheet.show(
                    baseActivity.supportFragmentManager,
                    PhonePrefixModalBottomsheet.TAG
                )
            } else {
                //todo show no available countries
            }
        })
    }

    override fun dialCodeSelected(country: Country) {
        phoneInputView.setCountryDetails(country.dialCode, country.flagPhoto)
        authViewModel.phoneCountry = country
    }

    abstract fun bindAdditionalView()

    abstract fun bindViewModelObservers()
}