package com.gymapp.features.onboarding.auth.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.helper.extensions.isValidEmail
import com.gymapp.helper.extensions.isValidName
import com.gymapp.helper.extensions.isValidPassword
import kotlinx.android.synthetic.main.dialog_auth.*
import kotlinx.android.synthetic.main.register_flow_input_otp.*
import kotlinx.android.synthetic.main.register_flow_input_phone.*
import kotlinx.android.synthetic.main.register_fow_input_email.*
import render.animations.Slide


class AuthRegisterDialogFragment : BaseAuthDialogFragment() {

    companion object {
        const val TAG = "AuthRegisterDialogFragment"

        fun newInstance(): AuthRegisterDialogFragment {
            return AuthRegisterDialogFragment()
        }
    }

    override fun bindAdditionalView() {

        registerUserBtn.setOnClickListener {

            if (!editTextFirstName.text.toString().isValidName()) {
                inputFirstName.error = "Invalid name"
                return@setOnClickListener
            }

            if (!editTextEmail.text.toString().isValidEmail()) {
                inputEmail.error = "Invalid email"
                return@setOnClickListener
            }

            if (!editTextPassword.text.toString().isValidPassword()) {
                inputPassword.error = "Invalid password"
                return@setOnClickListener
            }

            fullScreenLoading()

            authViewModel.registerUser(
                editTextFirstName.text.toString(),
                editTextEmail.text.toString(),
                editTextPassword.text.toString()
            )
        }

        editTextFirstName.doAfterTextChanged {
            inputFirstName.isErrorEnabled = false
        }
        editTextEmail.doAfterTextChanged {
            inputEmail.isErrorEnabled = false
        }
        editTextPassword.doAfterTextChanged {
            inputPassword.isErrorEnabled = false
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

    override fun bindPageText() {
        pageTitleTv.text = getString(R.string.auth_register_title_)
        signUpPhoneDescriptionTv.text = getString(R.string.auth_page_title_)
        signUpPhoneTitleTv.text = getString(R.string.auth_register_input_phone)
    }

}