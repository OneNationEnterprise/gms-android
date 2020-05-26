package com.gymapp.features.homepage.presentation

import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.homepage.domain.HomepageViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomepageActivity : BaseActivity(R.layout.activity_homepage) {

    lateinit var homepageViewModel: HomepageViewModel

    override fun setupViewModel() {
        homepageViewModel = getViewModel()


    }
}