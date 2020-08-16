package com.gymapp.features.store.presentation.payment

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.gym.type.InvoiceComponentType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.features.payment.subscriptions.presentation.adapter.item.SavedCardPaymentMethodItem
import com.gymapp.features.payment.subscriptions.presentation.bottomsheet.PaymentMethodsBottomSheet
import com.gymapp.features.profile.payment.presentation.SaveCardActivity
import com.gymapp.features.store.domain.payment.StorePaymentViewModel
import com.gymapp.features.store.presentation.cart.adapter.item.ProductItem
import com.gymapp.features.store.presentation.payment.adapter.ItemStorePaymentAdapter
import com.gymapp.helper.Constants
import com.gymapp.helper.ui.InAppBannerNotification
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_store_payment.*
import kotlinx.android.synthetic.main.activity_store_payment.amountDueValueTv
import kotlinx.android.synthetic.main.activity_store_payment.container
import kotlinx.android.synthetic.main.activity_store_payment.payBtn
import kotlinx.android.synthetic.main.activity_store_payment.paymentMethodImageIv
import kotlinx.android.synthetic.main.activity_store_payment.paymentMethodNameTv
import kotlinx.android.synthetic.main.activity_store_payment.serviceFeeValueTv
import kotlinx.android.synthetic.main.activity_store_payment.serviceFeelTv
import kotlinx.android.synthetic.main.activity_store_payment.subtotalValueTv
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class StorePaymentActivity : BaseActivity(R.layout.activity_store_payment) {

    lateinit var storePaymentViewModel: StorePaymentViewModel
    lateinit var productsInCart: MutableList<ProductItem>
    lateinit var addressId: String
    private var availablePaymentMethods = ArrayList<PaymentMethodInterface>()
    private var modalBottomSheet: PaymentMethodsBottomSheet? = null
    private val SAVE_CARD_ACTIVITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.getBundleExtra(Constants.arguments) ?: return

        productsInCart =
            Gson().fromJson<MutableList<ProductItem>>(
                bundle.getString(Constants.productsInCart),
                object : TypeToken<MutableList<ProductItem>>() {}.type
            )

        addressId = bundle.getString(Constants.addressId)!!

        setTitle("Payment")

        paymentHolder.setOnClickListener {
            modalBottomSheet =
                PaymentMethodsBottomSheet(availablePaymentMethods, storePaymentViewModel)
            modalBottomSheet?.show(supportFragmentManager, PaymentMethodsBottomSheet.TAG)
        }

        initRecycleViewProducts(productsInCart)

        GlobalScope.launch {
            storePaymentViewModel.getInvoice(productsInCart)
        }
    }

    override fun setupViewModel() {
        storePaymentViewModel = getViewModel()
    }


    override fun bindViewModelObservers() {

        storePaymentViewModel.invoice.observe(this, Observer {
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

        storePaymentViewModel.paymentMethods.observe(this, Observer {
            availablePaymentMethods.addAll(it)
        })

        storePaymentViewModel.openAddCardActivity.observe(this, Observer {
            startActivityForResult(
                Intent(this, SaveCardActivity::class.java),
                SAVE_CARD_ACTIVITY_REQUEST_CODE
            )
        })

        storePaymentViewModel.selectedPaymentMethod.observe(this, Observer {
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

        storePaymentViewModel.error.observe(this, Observer {
            InAppBannerNotification.showErrorNotification(container, this, it)
        })
    }

    private fun initRecycleViewProducts(products: MutableList<ProductItem>) {

        val optionSetAdapter = ItemStorePaymentAdapter(products)

        val linearLayoutManager = LinearLayoutManager(this)
        itemsRv.apply {
            adapter = optionSetAdapter
            layoutManager = linearLayoutManager
        }
    }
}