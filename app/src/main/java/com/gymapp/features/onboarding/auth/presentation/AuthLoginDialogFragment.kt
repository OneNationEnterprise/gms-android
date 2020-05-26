package com.gymapp.features.onboarding.auth.presentation

import android.os.Bundle
import com.gymapp.R
import kotlinx.android.synthetic.main.dialog_auth.*
import kotlinx.android.synthetic.main.register_flow_input_phone.*

class AuthLoginDialogFragment : BaseAuthDialogFragment() {


    companion object {
        const val TAG = "AuthLoginDialogFragment"

        fun newInstance(): AuthLoginDialogFragment {
            return AuthLoginDialogFragment()
        }
    }

    override fun bindViewModelObservers() {}

    override fun bindAdditionalView() {}

    override fun bindPageText() {
        pageTitleTv.text = getString(R.string.login)
        signUpPhoneDescriptionTv.text = getString(R.string.auth_page_title_)
        signUpPhoneTitleTv.text = getString(R.string.auth_login_input_phone)
    }
}