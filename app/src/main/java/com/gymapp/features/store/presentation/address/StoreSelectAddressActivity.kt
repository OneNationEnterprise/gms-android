package com.gymapp.features.store.presentation.address

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cofedistrict.ui.features.store.cart.adapter.item.StoreProductItem
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.addresses.presentation.saveedit.SelectAddressActivity
import com.gymapp.features.store.domain.address.StoreAddressViewModel
import com.gymapp.features.store.presentation.address.adapter.AddressSelectedListener
import com.gymapp.features.store.presentation.address.adapter.StoreAddressAdapter
import com.gymapp.features.store.presentation.cart.adapter.item.ProductItem
import com.gymapp.features.store.presentation.payment.StorePaymentActivity
import com.gymapp.helper.Constants
import kotlinx.android.synthetic.main.activity_store_address.*
import kotlinx.android.synthetic.main.activity_store_address.payBtn
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.getViewModel

class StoreSelectAddressActivity : BaseActivity(R.layout.activity_store_address),
    AddressSelectedListener {

    lateinit var storeAddressViewModel: StoreAddressViewModel
    lateinit var activity: Activity
    lateinit var storeAddressAdapter: StoreAddressAdapter
    lateinit var productsInCart: MutableList<ProductItem>
    lateinit var selectedAddressId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this

        val bundle = intent.getBundleExtra(Constants.arguments) ?: return

        productsInCart =
            Gson().fromJson<MutableList<ProductItem>>(
                bundle.getString(Constants.openSelectAddress),
                object : TypeToken<MutableList<ProductItem>>() {}.type
            )

        setTitle("Select address")
    }

    override fun onResume() {
        super.onResume()
        storeAddressViewModel.getAddresses()
    }


    override fun setupViewModel() {
        storeAddressViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        storeAddressViewModel.addressesList.observe(this, Observer {
            storeAddressAdapter =
                StoreAddressAdapter(
                    it,
                    activity as AddressSelectedListener,
                    ""
                )
            addressesRv.apply {
                adapter = storeAddressAdapter
                layoutManager = LinearLayoutManager(context)
            }
        })
    }

    override fun onAddNewAddressSelected() {
        val intent = Intent(this, SelectAddressActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        startActivity(intent)
    }

    override fun onAddressSelected(id: String) {
        storeAddressAdapter.setSelectedAddress(id)
        selectedAddressId = id

        payBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F95365"))
        payBtn.setOnClickListener {

            val intent = Intent(this, StorePaymentActivity::class.java)
            val args = Bundle()

            args.putString(Constants.productsInCart, Gson().toJson(productsInCart))
            args.putString(Constants.addressId, Gson().toJson(selectedAddressId))
            intent.putExtra(Constants.arguments, args)

            startActivity(intent)
        }
    }


}