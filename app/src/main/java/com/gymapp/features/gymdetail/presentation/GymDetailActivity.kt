package com.gymapp.features.gymdetail.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.gymdetail.domain.GymDetailViewModel
import com.gymapp.helper.Constants
import com.gymapp.main.data.model.gym.Gym
import kotlinx.android.synthetic.main.activity_gym_detail.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class GymDetailActivity : BaseActivity(R.layout.activity_gym_detail) {

    lateinit var gymDetailViewModel: GymDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gymDetailViewModel.setupGymData(
            intent.getBundleExtra(Constants.arguments)?.getString(Constants.gymId)
        )
    }

    override fun setupViewModel() {
        gymDetailViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        gymDetailViewModel.gym.observe(this, Observer {
            setupGymData(it)
        })
    }

    private fun setupGymData(gym: Gym) {
        val images = gym.images

        val adapter = ImageGalleryAdapter(images)
        gymImagesViewPager.adapter = adapter

        dotsIndicator.setViewPager2(gymImagesViewPager)

        gymNameTv.text = gym.name

        directionsContainer.setOnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:q=${gym.address.geoLocation.coordinates?.get(1)}," +
                    "${gym.address.geoLocation.coordinates?.get(0)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        callContainer.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:${gym.phone}")
            startActivity(intent);
        }


    }
}