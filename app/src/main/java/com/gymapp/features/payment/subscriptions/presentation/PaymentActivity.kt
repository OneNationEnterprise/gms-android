package com.gymapp.features.payment.subscriptions.presentation

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.apollographql.apollo.gym.CustomerCardTokenSaveMutation
import com.apollographql.apollo.gym.type.InvoiceComponentType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.classes.data.model.ClassDate
import com.gymapp.features.payment.subscriptions.domain.PaymentViewModel
import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.features.payment.subscriptions.presentation.adapter.item.SavedCardPaymentMethodItem
import com.gymapp.features.payment.subscriptions.presentation.bottomsheet.BottomSheetSelectedPaymentMethodsListener
import com.gymapp.features.payment.subscriptions.presentation.bottomsheet.PaymentMethodsBottomSheet
import com.gymapp.features.profile.payment.presentation.SaveCardActivity
import com.gymapp.helper.Constants
import com.gymapp.helper.SubscriptionType
import com.gymapp.helper.date.DateTimeTypeAdapter
import com.gymapp.helper.ui.InAppBannerNotification
import com.gymapp.main.data.model.classes.Class
import com.gymapp.main.data.model.subscription.Subscription
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_profile_cards_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PaymentActivity : BaseActivity(R.layout.activity_payment) {

    lateinit var paymentViewModel: PaymentViewModel
    private var availablePaymentMethods = ArrayList<PaymentMethodInterface>()
    private var modalBottomSheet: PaymentMethodsBottomSheet? = null
    private val SAVE_CARD_ACTIVITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.getBundleExtra(Constants.arguments) ?: return

        setTitle("Payment")

        initView(bundle)

        paymentHolder.setOnClickListener {
            modalBottomSheet = PaymentMethodsBottomSheet(availablePaymentMethods, paymentViewModel)
            modalBottomSheet?.show(supportFragmentManager, PaymentMethodsBottomSheet.TAG)
        }
    }

    override fun setupViewModel() {
        paymentViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {

        paymentViewModel.invoice.observe(this, Observer {
            for (invoive in it) {
                when (invoive.type) {
                    InvoiceComponentType.SERVICE_FEE -> {
                        serviceFeelTv.visibility = View.VISIBLE
                        serviceFeeValueTv.visibility = View.VISIBLE
                        serviceFeeValueTv.text = invoive.value
                    }
                    InvoiceComponentType.AMOUNT_DUE -> {
                        amountDueValueTv.text = invoive.value
                    }
                    InvoiceComponentType.SUBTOTAL -> {
                        subtotalValueTv.text = invoive.value
                    }
                }
            }
        })

        paymentViewModel.paymentMethods.observe(this, Observer {
            availablePaymentMethods.addAll(it)
        })

        paymentViewModel.openAddCardActivity.observe(this, Observer {
            startActivityForResult(Intent(this, SaveCardActivity::class.java), SAVE_CARD_ACTIVITY_REQUEST_CODE)
        })

        paymentViewModel.selectedPaymentMethod.observe(this, Observer {
            paymentMethodNameTv.text =
                (it as SavedCardPaymentMethodItem).paymentMethodDetails.fragments.paymentMethodFields.name

            if (it.paymentMethodDetails.fragments.paymentMethodFields.imageUrl != null) {
                Picasso.get()
                    .load(it.paymentMethodDetails.fragments.paymentMethodFields.imageUrl)
                    .into(paymentMethodImageIv)
            }

            payBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F95365"))
            payBtn.setOnClickListener {
//                paymentViewModel.hasClickedOnPaymentBtn()
            }
        })

        paymentViewModel.error.observe(this, Observer {
            InAppBannerNotification.showErrorNotification(container, this, it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SAVE_CARD_ACTIVITY_REQUEST_CODE) {
            if (data != null) {

                val bundle = data.getBundleExtra(Constants.arguments)

                val data =
                    Gson().fromJson<CustomerCardTokenSaveMutation.CustomerCardToken>(
                        bundle.getString(Constants.customerSavedCardData),
                        object : TypeToken<CustomerCardTokenSaveMutation.CustomerCardToken>() {}.type
                    )

                GlobalScope.launch {
                    paymentViewModel.cacheAddedCreditCardAndRefetchPaymentMethods(data)
                }
            }

            return
        }

    }

    private fun initView(bundle: Bundle) {
        val gson =
            GsonBuilder().registerTypeAdapter(DateTime::class.java, DateTimeTypeAdapter()).create()

        val gymName = bundle.getString(Constants.gymName)
        locationNameTv.text = gymName

        val subscription = gson.fromJson<Subscription>(
            bundle.getString(Constants.subscriptionData),
            object : TypeToken<Subscription>() {}.type
        )

        if (subscription.type != null) {

            titleTv.text = subscription.name

            when (subscription.type) {
                SubscriptionType.PASS -> {
                    startDateTv.visibility = View.GONE
                    startDateValueTv.visibility = View.GONE
                    timeTv.visibility = View.GONE
                    timeValueTv.visibility = View.GONE

                    GlobalScope.launch {
                        paymentViewModel.getPassesInvoice(subscription.id)
                    }
                }
                SubscriptionType.MEMBERSHIP -> {
                    startDateTv.visibility = View.GONE
                    startDateValueTv.visibility = View.GONE
                    timeTv.visibility = View.GONE
                    timeValueTv.visibility = View.GONE

                    GlobalScope.launch {
                        paymentViewModel.getMemberInvoice(subscription.id)
                    }
                }
            }
        } else {
            val gymClassData = gson.fromJson<Class>(
                bundle.getString(Constants.subscriptionData),
                object : TypeToken<Class>() {}.type
            )

            val classDate = gson.fromJson<ClassDate>(
                bundle.getString(Constants.classDate),
                object : TypeToken<ClassDate>() {}.type
            )

            titleTv.text = gymClassData.name

            startDateTv.visibility = View.VISIBLE
            startDateValueTv.visibility = View.VISIBLE
            startDateValueTv.text = classDate.dateToBeShownInAdapter

            timeTv.visibility = View.VISIBLE
            timeValueTv.visibility = View.VISIBLE
            timeValueTv.text = gymClassData.openTime

            GlobalScope.launch {
                paymentViewModel.getGymClassInvoice(gymClassData.id)
            }
        }

    }

}