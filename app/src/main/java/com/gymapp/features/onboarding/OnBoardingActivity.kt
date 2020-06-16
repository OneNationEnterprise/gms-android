package com.gymapp.features.onboarding

import android.os.Bundle
import android.view.View
import androidx.transition.TransitionManager
import androidx.viewpager2.widget.ViewPager2
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.onboarding.auth.presentation.AuthLoginDialogFragment
import com.gymapp.features.onboarding.auth.presentation.AuthRegisterDialogFragment
import com.gymapp.helper.view.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnBoardingActivity : BaseActivity(R.layout.activity_onboarding) {

    private var currentItemPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBtn.setOnClickListener {
            AuthRegisterDialogFragment.newInstance()
                .show(supportFragmentManager, AuthRegisterDialogFragment.TAG)
        }

        loginTv.setOnClickListener {
            AuthLoginDialogFragment.newInstance()
                .show(supportFragmentManager, AuthLoginDialogFragment.TAG)
        }

        continueBtn.setOnClickListener {
            viewPager2.currentItem = currentItemPage + 1
        }

        initViewPager()
    }

    override fun setupViewModel() {}

    override fun bindViewModelObservers() {}

    private fun initViewPager() {

        val viewPagerInfoTextList = listOf(
            Pair(
                getString(R.string.onboarding_step_one_title),
                getString(R.string.onboarding_step_one_description)
            ),
            Pair(
                getString(R.string.onboarding_step_two_title),
                getString(R.string.onboarding_step_two_description)
            ),
            Pair(
                getString(R.string.onboarding_step_three_title),
                getString(R.string.onboarding_step_three_description)
            ),
            Pair(
                getString(R.string.onboarding_step_four_title),
                getString(R.string.onboarding_step_four_description)
            )
        )

        val adapter = OnBoardingTextAdapter(viewPagerInfoTextList)
        viewPager2.adapter = adapter

        val zoomOutPageTransformer = ZoomOutPageTransformer()
        viewPager2.setPageTransformer { page, position ->
            zoomOutPageTransformer.transformPage(page, position)
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentItemPage = position

                TransitionManager.beginDelayedTransition(actionBtnContainer)

                if (position == viewPagerInfoTextList.size - 1) {
                    registerBtn.visibility = View.VISIBLE
                    loginTv.visibility = View.VISIBLE
                    continueBtn.visibility = View.GONE
                } else {
                    registerBtn.visibility = View.GONE
                    loginTv.visibility = View.GONE
                    continueBtn.visibility = View.VISIBLE
                }

                TransitionManager.endTransitions(actionBtnContainer)
            }
        })

        dotsIndicator.setViewPager2(viewPager2)
    }
}