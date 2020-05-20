package com.gymapp.features.onboarding.auth.presentation

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseDialogFragment
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
    lateinit var authViewModel : AuthViewModel

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

        continueWithOtpBtn.setOnClickListener {
            render.setAnimation(Slide().OutLeft(registerPhoneLayout))
            render.start()
            render.setAnimation(Slide().InRight(registerOtpLayout))
            registerOtpLayout.visibility = View.VISIBLE
            render.start()
            registerPhoneLayout.visibility = View.GONE
        }

        otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {}

            override fun onOTPComplete(otp: String) {
                render.setAnimation(Slide().OutLeft(registerOtpLayout))
                render.start()
                render.setAnimation(Slide().InRight(registerEmailLayout))
                registerEmailLayout.visibility = View.VISIBLE
                render.start()
                registerOtpLayout.visibility = View.GONE
            }

        }

        authViewModel = getViewModel()

        authViewModel.getAvailableCountries()?.observe(viewLifecycleOwner, Observer {
            phoneInputView.setCountryDetails(it[1].dialCode, it[1].flagPhoto)
        })

    }


}