package com.gymapp.features.onboarding.auth.presentation

import android.view.View
import com.gymapp.R
import com.gymapp.helper.LOGIN_PATH
import kotlinx.android.synthetic.main.dialog_auth.*
import kotlinx.android.synthetic.main.login_flow_with_email.*
import kotlinx.android.synthetic.main.register_flow_input_phone.*

class AuthLoginDialogFragment(path: LOGIN_PATH? = LOGIN_PATH.HOMEPAGE) :
    BaseAuthDialogFragment(path) {

    companion object {
        const val TAG = "AuthLoginDialogFragment"

        fun newInstance(path: LOGIN_PATH? = LOGIN_PATH.HOMEPAGE): AuthLoginDialogFragment {
            return AuthLoginDialogFragment(path)
        }
    }

    override fun bindViewModelObservers() {}

    override fun bindAdditionalView() {

        continueLoginWithEmailTv.visibility = View.VISIBLE

        continueLoginWithEmailTv.setOnClickListener {
            authInputPhoneLayout.visibility = View.GONE
            loginEmailLayout.visibility = View.VISIBLE
        }

        loginBtn.setOnClickListener {
            fullScreenLoading(true)
            authViewModel.loginWithEmailAndPassword(
                loginEditTextEmail.text.toString(),
                loginEditTextPassword.text.toString()
            )
        }
    }

    override fun bindPageText() {
        pageTitleTv.text = getString(R.string.login)
        signUpPhoneDescriptionTv.text = getString(R.string.auth_page_title_)
        signUpPhoneTitleTv.text = getString(R.string.auth_login_input_phone)
    }
}