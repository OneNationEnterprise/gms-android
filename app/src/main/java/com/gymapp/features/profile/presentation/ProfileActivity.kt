package com.gymapp.features.profile.presentation

import android.os.Bundle
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity

class ProfileActivity : BaseActivity(R.layout.activity_profile) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCustomToolbarTitle(getString(R.string.profile))
    }

    override fun setupViewModel() {}
}