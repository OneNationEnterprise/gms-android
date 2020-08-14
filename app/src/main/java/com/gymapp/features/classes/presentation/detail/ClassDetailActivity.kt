package com.gymapp.features.classes.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.apollographql.apollo.gym.type.DifficultyLevel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.classes.data.model.ClassDate
import com.gymapp.features.classes.domain.ClassesViewModel
import com.gymapp.features.classes.presentation.detail.adapter.ClassImageAdapter
import com.gymapp.features.onboarding.auth.presentation.AuthLoginDialogFragment
import com.gymapp.features.payment.subscriptions.presentation.PaymentActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.LOGIN_PATH
import com.gymapp.main.data.model.classes.Class
import com.gymapp.main.data.model.subscription.Subscription
import kotlinx.android.synthetic.main.activity_class_detail.*
import kotlinx.android.synthetic.main.activity_class_detail.descriptionTv
import kotlinx.android.synthetic.main.activity_class_detail.dotsIndicator
import kotlinx.android.synthetic.main.activity_subscription.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ClassDetailActivity : BaseActivity(R.layout.activity_class_detail) {

    lateinit var classesViewModel: ClassesViewModel

    lateinit var selectedDate: ClassDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.getBundleExtra(Constants.arguments) ?: return

        classesViewModel.fetchClassData(bundle.getString(Constants.classId))

        selectedDate = Gson().fromJson<ClassDate>(
            bundle.getString(Constants.classDate),
            object : TypeToken<ClassDate>() {}.type
        )
    }

    override fun setupViewModel() {
        classesViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        classesViewModel.gymClass.observe(this, Observer {
            if (it == null) {
                return@Observer
            }

            setupView(it)
        })
    }

    private fun setupView(gymClass: Class) {

        startHourTv.text = gymClass.openTime
        titleNameTv.text = gymClass.name
        priceValueTv.text = gymClass.amount
        descriptionTv.text = gymClass.description
        instructorNameTv.text = "${gymClass.instructor?.firstName} ${gymClass.instructor?.lastName}"
        durationValueTv.text = gymClass.duration
        caloriesBurnedValueTv.text = getString(
            R.string.estimate_cals_burned,
            gymClass.estimatedCaloriesBurnt
        )

        val spotsAlotted = gymClass.spotsAlotted ?: 0
        spotsValueTv.text = "${gymClass.spots - spotsAlotted}/${gymClass.spots}"

        when (gymClass.difficultyLevel) {
            DifficultyLevel.BEGINNER -> {
                difficultyValueTv.text =
                    getString(R.string.difficulty_beginner)
                difficultyValueTv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.green002
                    )
                )
            }
            DifficultyLevel.INTERMEDIATE -> {
                difficultyValueTv.text = getString(R.string.difficulty_intermediate)
                difficultyValueTv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.yellow005
                    )
                )
            }
            DifficultyLevel.ADVANCE -> {
                difficultyValueTv.text = getString(R.string.difficulty_advanced)
                difficultyValueTv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.red003
                    )
                )
            }
            else -> {
                difficultyValueTv.visibility = View.GONE
            }
        }

        val images = gymClass.images ?: return

        val imagesAdapter = ClassImageAdapter(images)

        imagesViewPager.adapter = imagesAdapter

        dotsIndicator.setViewPager2(imagesViewPager)

        buyMembershipBtn.setOnClickListener {

            if (FirebaseAuth.getInstance().currentUser == null) {
                AuthLoginDialogFragment.newInstance(LOGIN_PATH.PAYMENT)
                    .show(supportFragmentManager, AuthLoginDialogFragment.TAG)
                return@setOnClickListener
            }

            val intent = Intent(this, PaymentActivity::class.java)

            val args = Bundle()

            args.putString(
                Constants.subscriptionData,
                Gson().toJson(gymClass)
            )

            args.putString(
                Constants.classDate,
                Gson().toJson(selectedDate)
            )

            args.putString(Constants.gymName, gymClass.gymName)

            intent.putExtra(Constants.arguments, args)

            startActivity(intent)
        }
    }
}