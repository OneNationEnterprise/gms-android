package com.gymapp.features.mapview.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.gymdetail.presentation.GymDetailActivity
import com.gymapp.features.mapview.domain.GymClickListener
import com.gymapp.features.mapview.domain.MapViewViewModel
import com.gymapp.helper.Constants
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.helper.extensions.toDp
import com.gymapp.helper.view.BitmapHelper
import com.gymapp.main.data.model.gym.Gym
import com.mikepenz.materialize.util.KeyboardUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_map_view.*
import kotlinx.android.synthetic.main.bottomsheet_mapview_gyms_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.displayMetrics
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.Exception

class MapViewActivity : BaseActivity(R.layout.activity_map_view), OnMapReadyCallback,
    GymClickListener, GoogleMap.OnMarkerClickListener {

    lateinit var mapViewModel: MapViewViewModel
    private var googleMap: GoogleMap? = null
    private lateinit var gymsAdapter: MapViewGymListAdapter
    private lateinit var brandsListBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gymsAdapter = MapViewGymListAdapter(ArrayList(), this)

        setupBottomSheet()

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        customBackIv.setOnClickListener {
            onBackPressed()
        }

        placeSearchEt.doAfterTextChanged {
            mapViewModel.updateFilteredGymsList(it.toString())
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {

        googleMap = p0

        googleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                UserCurrentLocalization.position, 10f
            )
        )

        askPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            acceptedblock = {
                googleMap?.isMyLocationEnabled = true
            }).onDeclined { _ -> }

        googleMap?.setOnMarkerClickListener(this)

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
                    markerOptions.position(
                        LatLng(
                            gym.address.geoLocation.coordinates!![1]!!,
                            gym.address.geoLocation.coordinates[0]!!
                        )
                    )

                    val marker = googleMap?.addMarker(markerOptions)
                    marker?.tag = (gym.gymId)
                }

                override fun onError(e: Exception?) {

                }
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
            updateGymsList(it)
            for (gym in it) {
                addMarker(gym)
            }
        })
    }

    private fun updateGymsList(gyms: ArrayList<Gym>) {
        gymsAdapter.updateList(gyms)

        val linearLayoutManager = LinearLayoutManager(this)

        // init locations recycleview
        gymsListRv.apply {
            adapter = gymsAdapter
            layoutManager = linearLayoutManager
        }

        brandsListBottomSheetBehavior.peekHeight =
            resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_shown)

        brandsListBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setupBottomSheet() {

        brandsListBottomSheetBehavior = BottomSheetBehavior.from(gymsListContainer)
        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        KeyboardUtil.hideKeyboard(this@MapViewActivity)
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        KeyboardUtil.hideKeyboard(this@MapViewActivity)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        }

        brandsListBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback)
        brandsListBottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL

        brandsListBottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_hidden)
        brandsListBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onGymSelected(gymId: String) {
        val intent = Intent(this, GymDetailActivity::class.java)
        val args = Bundle()

        args.putString(Constants.gymId, gymId)
        intent.putExtra(Constants.arguments, args)

        startActivity(intent)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0 != null) {
            onGymSelected(p0.tag.toString())
        }
        return true
    }
}