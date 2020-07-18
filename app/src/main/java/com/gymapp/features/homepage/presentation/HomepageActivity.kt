package com.gymapp.features.homepage.presentation

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.homepage.domain.HomepageViewModel
import com.gymapp.features.profile.main.presentation.ProfileActivity
import kotlinx.android.synthetic.main.user_icon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomepageActivity : BaseActivity(R.layout.activity_homepage) {

    lateinit var homepageViewModel: HomepageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userLayoutContainer.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun setupViewModel() {
        homepageViewModel = getViewModel()
        GlobalScope.launch(Dispatchers.Main) {
            homepageViewModel.fetchGymList()
        }
    }

    override fun bindViewModelObservers() {
        homepageViewModel.user.observe(this, Observer {
            if (it != null) {
                loggedInInitialsTextView.text = it.fullName[0].toString()
            } else {
                //TODO - set profile public picture
            }
        })
    }


}