package com.gymapp.features.gymdetail.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.gym.GymClassCategoriesQuery
import com.apollographql.apollo.gym.type.GlobalStatusType
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.classes.presentation.list.ClassesActivity
import com.gymapp.features.gymdetail.domain.GymDetailViewModel
import com.gymapp.features.gymdetail.presentation.adapter.AmenityAdapter
import com.gymapp.features.gymdetail.presentation.adapter.CategorySelectedListener
import com.gymapp.features.gymdetail.presentation.adapter.ClassCategoriesAdapter
import com.gymapp.features.gymdetail.presentation.adapter.ImageGalleryAdapter
import com.gymapp.features.subscriptions.presentation.SubscriptionActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.DateHelper
import com.gymapp.helper.SubscriptionType
import com.gymapp.main.data.model.gym.Gym
import kotlinx.android.synthetic.main.activity_gym_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class GymDetailActivity : BaseActivity(R.layout.activity_gym_detail), CategorySelectedListener {

    lateinit var gymDetailViewModel: GymDetailViewModel
    private var gymName = ""
    lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bundle = intent.getBundleExtra(Constants.arguments) ?: return

        val gymId = bundle.getString(Constants.gymId) ?: return


        GlobalScope.launch {
            gymDetailViewModel.setupGymData(gymId)
        }

        backArrowIv.setOnClickListener {
            onBackPressed()
        }

        passesContainer.setOnClickListener {
            openSubscriptionClass(SubscriptionType.PASS, gymId)
        }

        buyMembershipBtn.setOnClickListener {
            openSubscriptionClass(SubscriptionType.MEMBERSHIP, gymId)
        }

        seeAllTv.setOnClickListener {
            openClassList(gymId, screenName = getString(R.string.all_classes))
        }
    }

    override fun setupViewModel() {
        gymDetailViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        gymDetailViewModel.gym.observe(this, Observer {
            setupGymData(it)
        })

        gymDetailViewModel.gymClassCategories.observe(this, Observer {

            classesTv.visibility = View.VISIBLE
            seeAllTv.visibility = View.VISIBLE

            val optionSetAdapter = ClassCategoriesAdapter(it, this)

            val linearLayoutManager =
                LinearLayoutManager(this@GymDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            classCategoriesRv.apply {
                adapter = optionSetAdapter
                layoutManager = linearLayoutManager
            }
        })
    }

    private fun setupGymData(gym: Gym) {
        val images = gym.images

        val imagesAdapter =
            ImageGalleryAdapter(
                images
            )
        gymImagesViewPager.adapter = imagesAdapter

        dotsIndicator.setViewPager2(gymImagesViewPager)

        gymName = gym.name

        gymNameTv.text = gymName

        if (gym.description.isNullOrEmpty()) {
            aboutTitleTv.visibility = View.GONE
        }

        aboutDescriptionTv.text = gym.description

        scheduleTv.text = DateHelper.getGymDetailTime(
            gym.opening?.operatingTime?.begin,
            gym.opening?.operatingTime?.end
        )

        directionsContainer.setOnClickListener {
            val gmmIntentUri = Uri.parse(
                "google.navigation:q=${gym.address.geoLocation.coordinates?.get(1)}," +
                        "${gym.address.geoLocation.coordinates?.get(0)}"
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        callContainer.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:${gym.phone}")
            startActivity(intent);
        }

        if (gym.amenityList.isNullOrEmpty()) {
            amenitiesTitleTv.visibility = View.GONE
            return
        }

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }

        val activeAmenityList = gym.amenityList.filter {
            it.status == GlobalStatusType.ACTIVE
        }

        amenitiesRv.apply {
            adapter = AmenityAdapter(activeAmenityList)
            layoutManager = gridLayoutManager
        }
    }

    private fun openSubscriptionClass(subscriptionTypeType: SubscriptionType, gymId: String) {
        val intent = Intent(this, SubscriptionActivity::class.java)
        val args = Bundle()

        args.putString(
            Constants.gymId,
            gymId
        )

        args.putString(
            Constants.gymName,
            gymName
        )
        args.putSerializable(
            Constants.subscriptionType,
            subscriptionTypeType
        )
        intent.putExtra(Constants.arguments, args)

        startActivity(intent)
    }

    private fun openClassList(gymId: String, categoryId: String? = null, screenName: String) {
        val intent = Intent(this, ClassesActivity::class.java)
        val args = Bundle()

        args.putString(Constants.gymId, gymId)

        args.putString(Constants.categoryId, categoryId)

        args.putString(Constants.classesListPageName, screenName)

        intent.putExtra(Constants.arguments, args)

        startActivity(intent)
    }

    override fun onCategorySelected(category: GymClassCategoriesQuery.List) {
        openClassList(bundle.getString(Constants.gymId)!!, category.id, category.name)
    }


}