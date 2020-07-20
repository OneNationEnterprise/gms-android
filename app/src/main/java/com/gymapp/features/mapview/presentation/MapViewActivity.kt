package com.gymapp.features.mapview.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.mapview.domain.MapViewViewModel
import com.gymapp.helper.Constants
import com.gymapp.helper.extensions.toDp
import com.gymapp.helper.view.BitmapHelper
import com.gymapp.main.data.model.gym.Gym
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.imageResource
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.Exception

class MapViewActivity : BaseActivity(R.layout.activity_map_view), OnMapReadyCallback {

    lateinit var mapViewModel: MapViewViewModel
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {

        googleMap = p0

//            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(homeActivityListener.getCurrentSelectedAddress().geoPosition, 7f))
//
//            askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, acceptedblock = {
//                googleMap?.isMyLocationEnabled = true
//            }).onDeclined { _ -> }


//        googleMap?.setOnMarkerClickListener(mapViewBrandLocationVM)


        val locationButton =
            (((supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).view?.findViewById<View>(
                Integer.parseInt("1")
            )?.parent as View).findViewById<View>(Integer.parseInt("2")) as ImageView)
        locationButton.visibility = View.GONE

        GlobalScope.launch {
            mapViewModel.setupMapView(
                intent.getBundleExtra(Constants.arguments)?.getString(Constants.brandId)
            )
        }
    }


    private fun addMarker(gym: Gym) {
        val profileView = ImageView(this)
        Picasso.get().load(gym.brand.logo)
            .into(profileView, object : Callback {
                override fun onSuccess() {

                    val innerBitmap = profileView.drawable.toBitmap()

                    val markerOptions = MarkerOptions()

                    var image: Bitmap
                    image = Bitmap.createScaledBitmap(
                        innerBitmap,
                        (displayMetrics.widthPixels / 4).toDp(),
                        (displayMetrics.widthPixels / 4).toDp(),
                        false
                    )
                    //add round corner
                    image = BitmapHelper.setRoundedCorners(image, 14f)
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(image))
                    markerOptions.position(LatLng(gym.address.latitude, gym.address.longitude))

                    val marker = googleMap?.addMarker(markerOptions)
                    marker?.tag = ("${gym.gymId}###${gym.brand.brandId}")
                }

                override fun onError(e: Exception?) {}
            })
    }


    override fun setupViewModel() {
        mapViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        mapViewModel.filteredGymsList.observe(this, Observer {

            googleMap?.clear()

            if (it.isNullOrEmpty()) {
                return@Observer
            }

            for (gym in it) {
                addMarker(gym)
            }
        })
    }
}