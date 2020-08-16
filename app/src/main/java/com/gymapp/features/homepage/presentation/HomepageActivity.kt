package com.gymapp.features.homepage.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.homepage.domain.HomepageViewModel
import com.gymapp.features.homepage.presentation.adapter.HomepageGymClickListener
import com.gymapp.features.mapview.presentation.MapViewActivity
import com.gymapp.features.onboarding.OnBoardingActivity
import com.gymapp.features.profile.main.presentation.ProfileActivity
import com.gymapp.features.store.presentation.homepage.StoreActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.view.CustomPagerTransformer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.user_icon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomepageActivity : BaseActivity(R.layout.activity_homepage), HomepageGymClickListener {

    lateinit var homepageViewModel: HomepageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loggedInInitialsTextView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        loggedInHeaderImageProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        viewMapTv.setOnClickListener {
            startActivity(Intent(this, MapViewActivity::class.java))
        }
        loggedOutHeaderImageProfile.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
        storeContainerImage.setOnClickListener {
            startActivity(Intent(this, StoreActivity::class.java))
        }
    }

    override fun setupViewModel() {
        homepageViewModel = getViewModel()
        GlobalScope.launch(Dispatchers.Main) {
            homepageViewModel.fetchGymList(this@HomepageActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        homepageViewModel.fetchCurrentUser()
    }

    override fun bindViewModelObservers() {
        homepageViewModel.user.observe(this, Observer {
            if (it != null) {

                if (!it.photo.isNullOrEmpty()) {

                    loggedInHeaderImageProfile.visibility = View.VISIBLE
                    loggedInInitialsTextView.visibility = View.INVISIBLE
                    Picasso.get()
                        .load(it.photo)
                        .into(loggedInHeaderImageProfile)
                } else {
                    loggedInInitialsTextView.text = it.fullName[0].toString()
                }

            } else {
                loggedOutHeaderImageProfile.visibility = View.VISIBLE
                loggedInInitialsTextView.visibility = View.GONE
            }
        })

        homepageViewModel.nearByGyms.observe(this, Observer {
            if (it == null || it.isEmpty()) {
                zeroData.visibility = View.VISIBLE
                viewMapTv.visibility = View.GONE
            } else {
                zeroData.visibility = View.GONE
                viewMapTv.visibility = View.VISIBLE
                homepageViewModel.setupGymBrandsAdapter()
            }
        })

        homepageViewModel.gymBrandsList.observe(this, Observer {
            if (it.size > 0) {

                pager.apply {

                    adapter =
                        HomepageGymsItemAdapter(this@HomepageActivity, it, this@HomepageActivity)

                    offscreenPageLimit = 14

                    setPageTransformer(
                        true, CustomPagerTransformer(context)
                    )
                }

                pager.clipToPadding = false
                pager.pageMargin =
                    resources.getDimension(R.dimen.homepage_pager_image_padding).toInt()
            }
        })
    }

    override fun hasSelectedABrand(brandId: String) {
        val intent = Intent(this, MapViewActivity::class.java)
        val args = Bundle()

        args.putString(Constants.brandId, brandId)
        intent.putExtra(Constants.arguments, args)

        startActivity(intent)
    }

    override fun hasSelectedPasses(brandId: String) {
        //TODO
    }


}