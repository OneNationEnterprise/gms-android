package com.gymapp.main.launcher.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.florent37.runtimepermission.RuntimePermission.askPermission
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.homepage.presentation.HomepageActivity
import com.gymapp.features.onboarding.OnBoardingActivity
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.main.launcher.domain.LauncherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LauncherActivity : BaseActivity(R.layout.activity_launcher) {

    private lateinit var launcherViewModel: LauncherViewModel
    private lateinit var activity: Activity

    override fun setupViewModel() {
        activity = this
        launcherViewModel = getViewModel()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {

        askPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            acceptedblock = {

                val mFusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(activity)
                mFusedLocationClient.lastLocation
                    .addOnSuccessListener(activity) { location ->

                        //memory cache current postion
                        UserCurrentLocalization.position =
                            LatLng(location.latitude, location.longitude)

                        //fetch data
                        GlobalScope.launch(Dispatchers.Main) {
                            launcherViewModel.fetchData()
                        }
                    }
                    .addOnFailureListener {
                        //open map activity
                        startActivity(Intent(activity, SelectLocationActivity::class.java))
                    }
            }).onDeclined() { _ ->
            //open map activity
            startActivity(Intent(activity, SelectLocationActivity::class.java))
        }
    }

}