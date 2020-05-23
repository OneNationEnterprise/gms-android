package com.gymapp.features.onboarding.auth.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.gymapp.R
import kotlinx.android.synthetic.main.register_flow_input_otp.*
import kotlinx.android.synthetic.main.register_fow_input_email.*
import render.animations.Slide


class AuthRegisterDialogFragment : BaseAuthDialogFragment() {

    companion object {
        const val TAG = "AuthRegisterDialogFragment"

        fun newInstance(): AuthRegisterDialogFragment {
            return AuthRegisterDialogFragment()
        }
    }

    override fun bindAdditionalView(savedInstanceState: Bundle?) {

        registerUserBtn.setOnClickListener {
            authViewModel.registerUser("test1", "firebase@test2.kw", "password")
        }

    }

    override fun bindViewModelObservers() {

        authViewModel.showRegisterWithEmail.observe(viewLifecycleOwner, Observer {
            if (it) {
                render.setAnimation(Slide().OutLeft(authInputOtpLayout))
                render.start()
                render.setAnimation(Slide().InRight(registerEmailLayout))
                registerEmailLayout.visibility = View.VISIBLE
                render.start()
                authInputOtpLayout.visibility = View.GONE
            }
        })
    }

}