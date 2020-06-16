package com.gymapp.features.profile.settings.presentation

import android.os.Bundle
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity(R.layout.activity_settings) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomToolbarTitle(getString(R.string.settings))

        signOutContainer.setOnClickListener {

        }
    }

    override fun setupViewModel() {}
    override fun bindViewModelObservers() {}
}