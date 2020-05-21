package com.gymapp.main.launcher.presentation

import android.content.Intent
import android.os.Bundle
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.onboarding.OnBoardingActivity
import com.gymapp.main.launcher.domain.LauncherViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LauncherActivity : BaseActivity(R.layout.activity_launcher) {

    private lateinit var launcherViewModel: LauncherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, OnBoardingActivity::class.java))
    }

    override fun setupViewModel() {
        launcherViewModel = getViewModel()
        launcherViewModel.fetchData()
    }
}