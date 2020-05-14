package com.gymapp

import android.content.Intent
import android.os.Bundle
import com.gymapp.base.BaseActivity
import com.gymapp.features.onboarding.OnBoardingActivity

class LauncherActivity : BaseActivity(R.layout.activity_launcher) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, OnBoardingActivity::class.java))
    }
}