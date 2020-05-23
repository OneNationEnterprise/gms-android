package com.gymapp.features.onboarding.auth.presentation

import android.os.Bundle
import com.gymapp.R

class AuthLoginDialogFragment : BaseAuthDialogFragment() {


    companion object {
        const val TAG = "AuthRegisterDialogFragment"

        fun newInstance(): AuthLoginDialogFragment {
            return AuthLoginDialogFragment()
        }
    }

    override fun bindViewModelObservers() {

    }

    override fun bindAdditionalView(savedInstanceState: Bundle?) {

    }
}