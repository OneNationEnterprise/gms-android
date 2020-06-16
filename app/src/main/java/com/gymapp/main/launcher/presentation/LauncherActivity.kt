package com.gymapp.main.launcher.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.homepage.presentation.HomepageActivity
import com.gymapp.features.onboarding.OnBoardingActivity
import com.gymapp.main.launcher.domain.LauncherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LauncherActivity : BaseActivity(R.layout.activity_launcher) {

    private lateinit var launcherViewModel: LauncherViewModel

    override fun setupViewModel() {
        launcherViewModel = getViewModel()
        GlobalScope.launch(Dispatchers.Main) {
            launcherViewModel.fetchData()
        }
    }

    override fun bindViewModelObservers() {
        launcherViewModel.errorFetchingUser.observe(this, Observer {
            if (it == null) {
                startActivity(Intent(this, HomepageActivity::class.java))
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
        })
    }
}