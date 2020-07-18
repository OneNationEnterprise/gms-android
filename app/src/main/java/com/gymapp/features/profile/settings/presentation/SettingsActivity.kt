package com.gymapp.features.profile.settings.presentation

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.homepage.presentation.HomepageActivity
import com.gymapp.features.profile.settings.domain.SettingsViewModel
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SettingsActivity : BaseActivity(R.layout.activity_settings) {

    lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomToolbarTitle(getString(R.string.settings))

        signOutContainer.setOnClickListener {
            settingsViewModel.signOut()
        }
    }

    override fun setupViewModel() {
        settingsViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        settingsViewModel.hasSignedOut.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, HomepageActivity::class.java))
            }
        })
    }
}