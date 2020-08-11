package com.gymapp.features.profile.addresses.presentation.saveedit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.gymapp.features.profile.addresses.domain.SelectAddressViewModel
import com.gymapp.helper.Constants
import com.gymapp.helper.ui.InAppBannerNotification
import com.gymapp.main.launcher.presentation.adapter.PlacesAutoCompleteAdapter
import com.mikepenz.materialize.util.KeyboardUtil
import kotlinx.android.synthetic.main.activity_select_address.*
import kotlinx.android.synthetic.main.bottomsheet_api_autocomplete.*
import org.jetbrains.anko.imageResource
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SelectAddressActivity : BaseActivity(R.layout.activity_select_address), SelectAddressView,
    OnMapReadyCallback,
    PlacesAutoCompleteAdapter.ClickListener {

    lateinit var selectAddressViewModel: SelectAddressViewModel
    lateinit var activity: BaseActivity

    private lateinit var googleMap: GoogleMap

    private lateinit var autoCompleteAdapter: PlacesAutoCompleteAdapter
    private lateinit var brandsListBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var selectedLatLng: LatLng

    override fun setupViewModel() {
        selectAddressViewModel = getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectAddressViewModel.selectAddressView = this

        activity = this

        toolbarTitle.text = "Select location"

        initBottomSheet()

        setSearchCollapsed()

        initPlaces()

        initMap()

        confirmAddress.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            selectedLatLng = LatLng(
                googleMap.cameraPosition.target.latitude,
                googleMap.cameraPosition.target.longitude
            )
            selectAddressViewModel.fetchAddress(
                googleMap.cameraPosition.target.latitude,
                googleMap.cameraPosition.target.longitude,
                this
            )
        }
        backIv.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun bindViewModelObservers() {

    }

    private fun initMap() {
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        Constants.dubaiLatitude,
                        Constants.dubaiLongitude
                    ), 7f
                )
            )


            val locationButton =
                (((supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).view?.findViewById<View>(
                    Integer.parseInt("1")
                )?.parent as View).findViewById<View>(Integer.parseInt("2")) as ImageView)
            locationButton.imageResource = R.drawable.ic_locate_me

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

        }
    }


    override fun cofeNotDeliverOnThatLocation() {
        progressBar.visibility = View.GONE
        InAppBannerNotification.showErrorNotification(
            mapContainer,
            baseContext,
            "Location not available"
        )
    }

    override fun showAddress(countryCode: String) {
        progressBar.visibility = View.GONE

        val intent = Intent(this, SaveEditAddressActivity::class.java)
        intent.putExtra(Constants.locationAddressDetails, countryCode)
        intent.putExtra(Constants.latLng, selectedLatLng)
        startActivity(intent)
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
            activity,
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
    }

    private val filterTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.toString() != "") {
                autoCompleteAdapter.getFilter().filter(s.toString())
                if (placesRv.getVisibility() == View.GONE) {
                    placesRv.visibility = View.VISIBLE
                }
            } else {

                if (placesRv.visibility == View.VISIBLE) {
                    placesRv.visibility = View.GONE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    }

    override fun click(place: Place?) {
        if (place != null && place.latLng != null) {

            selectedLatLng = place.latLng!!
            selectAddressViewModel.fetchAddress(
                place.latLng!!.latitude,
                place.latLng!!.longitude,
                this
            )

        } else {
            //TODO ERROR
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
                        KeyboardUtil.hideKeyboard(activity)
                        confirmAddress.visibility = View.GONE
                        cancelTxt.visibility = View.VISIBLE
                        placesRv.visibility = View.GONE
                        headerLine.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        cancelTxt.visibility = View.GONE
                        placesRv.visibility = View.GONE
                        KeyboardUtil.hideKeyboard(activity)
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


    override fun addressNotFound(message: String) {
        progressBar.visibility = View.GONE
        InAppBannerNotification.showErrorNotification(mapContainer, baseContext, message)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}