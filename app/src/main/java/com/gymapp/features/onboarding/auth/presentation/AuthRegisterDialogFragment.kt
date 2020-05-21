package com.gymapp.features.onboarding.auth.presentation

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseDialogFragment
import com.gymapp.features.homepage.HomepageActivity
import com.gymapp.features.onboarding.auth.domain.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlinx.android.synthetic.main.dialog_auth_register.*
import kotlinx.android.synthetic.main.register_flow_input_otp.*
import kotlinx.android.synthetic.main.register_flow_input_phone.*
import kotlinx.android.synthetic.main.register_fow_input_email.*
import render.animations.Render
import render.animations.Slide


class AuthRegisterDialogFragment : BaseDialogFragment(R.layout.dialog_auth_register) {

    lateinit var render: Render
    lateinit var authViewModel: AuthViewModel

    companion object {
        const val TAG = "AuthRegisterDialogFragment"

        fun newInstance(): AuthRegisterDialogFragment {
            return AuthRegisterDialogFragment()
        }
    }

    override fun bindView(savedInstanceState: Bundle?) {

        render = Render(context!!)
        render.setDuration(300)

        closeIv.setOnClickListener {
            dismiss()
        }

        setupViewModelAndBindObservers()

        continueWithOtpBtn.setOnClickListener {
            authViewModel.validatePhoneNumber(phoneInputView.getPhoneNumber(), baseActivity)
        }

        otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {}

            override fun onOTPComplete(otp: String) {
                authViewModel.validateOtp(otp)
            }
        }

        registerUserBtn.setOnClickListener {
            authViewModel.registerUser("test", "test@test.ru","parola")
        }

    }

    private fun setupViewModelAndBindObservers() {
        authViewModel = getViewModel()

        authViewModel.getAvailableCountries()?.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                phoneInputView.setCountryDetails(it[0].dialCode, it[0].flagPhoto)
                authViewModel.phoneCountry = it[0]
            }
        })

        authViewModel.showOtpView.observe(viewLifecycleOwner, Observer {
            if (it) {
                render.setAnimation(Slide().OutLeft(registerPhoneLayout))
                render.start()
                render.setAnimation(Slide().InRight(registerOtpLayout))
                registerOtpLayout.visibility = View.VISIBLE
                render.start()
                registerPhoneLayout.visibility = View.GONE

                otpView.requestFocusOTP()
            } else {
                render.setAnimation(Slide().OutLeft(registerOtpLayout))
                render.start()
                render.setAnimation(Slide().InRight(registerPhoneLayout))
                registerPhoneLayout.visibility = View.VISIBLE
                render.start()
                registerOtpLayout.visibility = View.GONE
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

        authViewModel.showRegisterWithEmail.observe(viewLifecycleOwner, Observer {
            if (it) {
                render.setAnimation(Slide().OutLeft(registerOtpLayout))
                render.start()
                render.setAnimation(Slide().InRight(registerEmailLayout))
                registerEmailLayout.visibility = View.VISIBLE
                render.start()
                registerOtpLayout.visibility = View.GONE
            }
        })

        authViewModel.registerErrorMessage.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                //registration successful, open next page
                startActivity(Intent(activity, HomepageActivity::class.java))
            } else {
                //show error message
            }
        })

    }

}