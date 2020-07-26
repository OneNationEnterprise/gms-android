package com.gymapp.features.subscriptions.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.gymdetail.presentation.adapter.ClassCategoriesAdapter
import com.gymapp.features.subscriptions.domain.SubscriptionViewModel
import com.gymapp.features.subscriptions.presentation.adapter.SubscriptionAdapter
import com.gymapp.helper.Constants
import com.gymapp.helper.SubscriptionType
import com.gymapp.helper.ui.InAppBannerNotification
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_gym_detail.*
import kotlinx.android.synthetic.main.activity_subscription.*
import kotlinx.android.synthetic.main.activity_subscription.backArrowIv
import kotlinx.android.synthetic.main.activity_subscription.gymLogoIv
import kotlinx.android.synthetic.main.dialog_auth.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.textColor
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * This class is used to display Passes or Memberships options
 */
class SubscriptionActivity : BaseActivity(R.layout.activity_subscription) {

    lateinit var subscriptionViewModel: SubscriptionViewModel
    lateinit var subscriptionAdapter: SubscriptionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val type = intent.getBundleExtra(Constants.arguments)
            ?.getSerializable(Constants.subscriptionType) as SubscriptionType
        GlobalScope.launch {
            subscriptionViewModel.fetchData(
                intent.getBundleExtra(Constants.arguments)?.getString(Constants.gymId),
                type
            )
        }

        bindSubscriptionType(type)

        Picasso.get().load(intent.getBundleExtra(Constants.arguments)?.getString(Constants.gymLogo))
            .into(gymLogoIv)

        backArrowIv.setOnClickListener {
            onBackPressed()
        }
    }

    override fun setupViewModel() {
        subscriptionViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        subscriptionViewModel.subscriptionAdapterData.observe(this, Observer {


            if (it.subscriptionList.isEmpty()) {
                InAppBannerNotification.showErrorNotification(
                    subscriptionContainer,
                    this,
                    "No options available!"
                )
                return@Observer
            }

            subscriptionAdapter = SubscriptionAdapter(
                it.subscriptionList,
                it.selectedItemId,
                it.subscriptionSelectedListener
            )

            val linearLayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            subscriptionsRv.apply {
                adapter = subscriptionAdapter
                layoutManager = linearLayoutManager
            }
        })

        subscriptionViewModel.selectedSubscriptionId.observe(this, Observer {

            ViewCompat.setBackgroundTintList(
                buySubscriptionBtn,
                ColorStateList.valueOf(resources.getColor(R.color.red001))
            )

            buySubscriptionBtn.textColor = resources.getColor(R.color.white)

            subscriptionAdapter.updateSelectedItemId(it)
        })
    }


    private fun bindSubscriptionType(type: SubscriptionType) {
        when (type) {
            SubscriptionType.MEMBERSHIP -> {
                titleTv.text = getString(R.string.buy_membership_)
                descriptionTv.text = getString(R.string.buy_membership_description)
                recycleViewTitleTv.text = getString(R.string.select_membership)
            }
            SubscriptionType.PASS -> {
                titleTv.text = getString(R.string.buy_pass)
                descriptionTv.text = getString(R.string.buy_pass_description)
                recycleViewTitleTv.text = getString(R.string.select_pass)
            }
        }
    }
}