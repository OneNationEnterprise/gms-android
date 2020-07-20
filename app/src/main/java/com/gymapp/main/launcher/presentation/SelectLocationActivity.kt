package com.gymapp.main.launcher.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.homepage.presentation.HomepageActivity
import com.gymapp.features.onboarding.OnBoardingActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.UserCurrentLocalization
import com.gymapp.main.launcher.domain.LauncherViewModel
import com.gymapp.main.launcher.presentation.adapter.PlacesAutoCompleteAdapter
import com.mikepenz.materialize.util.KeyboardUtil
import kotlinx.android.synthetic.main.activity_select_location.*
import kotlinx.android.synthetic.main.bottomsheet_api_autocomplete.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SelectLocationActivity : BaseActivity(R.layout.activity_select_location), OnMapReadyCallback,
    PlacesAutoCompleteAdapter.ClickListener {

    private lateinit var autoCompleteAdapter: PlacesAutoCompleteAdapter
    private lateinit var brandsListBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var googleMap: GoogleMap
    private lateinit var launcherViewModel: LauncherViewModel

    private val context = this

    private val REQUEST_OPEN_SETTINGS = 1234

    override fun setupViewModel() {
        launcherViewModel = getViewModel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        initBottomSheet()

        setSearchCollapsed()

        initPlaces()

        toolbarTitle.text = getString(R.string.addresses_select_address)

//        if (!isLocationEnabled(this) && wasOpenFromSplashScreen) {
//            displayPromptForEnablingGPS()
//        }
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

    private fun displayPromptForEnablingGPS() {

        val dialogBuilder = AlertDialog.Builder(this);

        val inflater = this.layoutInflater

        val dialogView = inflater.inflate(R.layout.dialog_cofe_app_location, null);
        dialogBuilder.setView(dialogView)

        val yes = dialogView.findViewById<TextView>(R.id.yesTv)
        val no = dialogView.findViewById<TextView>(R.id.noTv)

        val alertDialog = dialogBuilder.create()

        no.setOnClickListener {
            alertDialog.dismiss()
        }

        yes.setOnClickListener {
            startActivityForResult(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                REQUEST_OPEN_SETTINGS
            )
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocationIfItIsAvailable() {

        showLoading()

        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.lastLocation
            .addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    cacheCurrentLocation(LatLng(location.latitude, location.longitude))
                } else {
                    hideLoading()
                }
            }
            .addOnFailureListener {
                hideLoading()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_OPEN_SETTINGS) {
            askPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                acceptedblock = {
                    getLastKnownLocationIfItIsAvailable()
                }).onDeclined { _ ->

            }
        }
    }

    private fun initPlaces() {
        Places.initialize(this, Constants.GOOGLE_MAPS_API)

        placeSearchEt.addTextChangedListener(filterTextWatcher)
        placeSearchEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                brandsListBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                brandsListBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        autoCompleteAdapter = PlacesAutoCompleteAdapter(this)

        placesRv.layoutManager = LinearLayoutManager(this)

        autoCompleteAdapter.setClickListener(this)

        placesRv.adapter = autoCompleteAdapter

        val dividerItemDecoration = DividerItemDecoration(
            context,
            (placesRv.layoutManager as LinearLayoutManager).orientation
        )
        placesRv.addItemDecoration(dividerItemDecoration);

        autoCompleteAdapter.notifyDataSetChanged()

        placeSearchEt.setOnClickListener {
            brandsListBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            headerLine.visibility = View.GONE
        }

        cancelTxt.setOnClickListener {
            brandsListBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        clearTextIv.setOnClickListener {
            placeSearchEt.setText("")
        }
    }


    private fun initBottomSheet() {
        brandsListBottomSheetBehavior = BottomSheetBehavior.from(apiAutocomplete)

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        confirmAddress.visibility = View.GONE
                        placesRv.visibility = View.VISIBLE
                        cancelTxt.visibility = View.VISIBLE
                        headerLine.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        KeyboardUtil.hideKeyboard(context)
                        confirmAddress.visibility = View.GONE
                        cancelTxt.visibility = View.VISIBLE
                        placesRv.visibility = View.GONE
                        headerLine.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        cancelTxt.visibility = View.GONE
                        placesRv.visibility = View.GONE
                        KeyboardUtil.hideKeyboard(context)
                        confirmAddress.visibility = View.VISIBLE
                        headerLine.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        }
        brandsListBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback)
        brandsListBottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL

    }

    private fun setSearchCollapsed() {
        brandsListBottomSheetBehavior.peekHeight =
            resources.getDimensionPixelSize(R.dimen.select_location_peek_height_collapsed)
        brandsListBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            googleMap = it
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        Constants.dubaiLatitude,
                        Constants.dubaiLongitude
                    ), 7f
                )
            )
        }
        askPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            acceptedblock = {
                googleMap.setMyLocationEnabled(true)
            }).onDeclined { _ -> }


        val locationButton =
            (((supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).view?.findViewById<View>(
                Integer.parseInt("1")
            )?.parent as View).findViewById<View>(Integer.parseInt("2")) as ImageView)

        locationButton.visibility = View.GONE

        val rlp = locationButton.layoutParams as (RelativeLayout.LayoutParams)
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(
            0,
            0,
            30,
            resources.getDimensionPixelSize(R.dimen.select_location_peek_height_collapsed) + 30
        );

        confirmAddress.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            cacheCurrentLocation(
                LatLng(
                    googleMap.cameraPosition.target.latitude,
                    googleMap.cameraPosition.target.longitude
                )
            )
        }
    }

    private val filterTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.toString() != "") {
                clearTextIv.visibility = View.VISIBLE
                autoCompleteAdapter.filter.filter(s.toString())
                if (placesRv.visibility == View.GONE) {
                    placesRv.visibility = View.VISIBLE
                }
            } else {
                clearTextIv.visibility = View.GONE
                if (placesRv.visibility == View.VISIBLE) {
                    placesRv.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    }


    fun cacheCurrentLocation(location: LatLng) {
        UserCurrentLocalization.setUserCurrentLocalization(location)
        //fetch data
        GlobalScope.launch(Dispatchers.Main) {
            launcherViewModel.fetchData()
        }
    }


    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun click(place: Place?) {
        if (place != null && place.latLng != null) {
            cacheCurrentLocation(place.latLng!!)
        } else {
            hideLoading()
        }
    }
}