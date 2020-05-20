package com.gymapp.features.onboarding

import android.os.Bundle
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.onboarding.auth.presentation.AuthRegisterDialogFragment
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnBoardingActivity : BaseActivity(R.layout.activity_onboarding) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBtn.setOnClickListener {
            AuthRegisterDialogFragment.newInstance()
                .show(supportFragmentManager, AuthRegisterDialogFragment.TAG)
        }
    }
}