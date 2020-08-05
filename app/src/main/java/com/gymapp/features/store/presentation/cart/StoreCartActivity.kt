package com.gymapp.features.store.presentation.cart

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.store.domain.products.cart.StoreCartViewModel
import com.gymapp.features.store.presentation.cart.adapter.StoreCartItemAdapter
import kotlinx.android.synthetic.main.activity_store_cart.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class StoreCartActivity : BaseActivity(R.layout.activity_store_cart) {

    private lateinit var storeCartItemsAdapter: StoreCartItemAdapter
    private lateinit var storeViewModel: StoreCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeCartItemsAdapter = StoreCartItemAdapter(ArrayList(), storeViewModel)

        storeViewModel.fetchData()

        proceedToCheckoutBtn.setOnClickListener {
//            storeViewModel.openCheckoutScreen()
        }
    }

    override fun setupViewModel() {
        storeViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {

        storeViewModel.cartItemList.observe(this, Observer {
            storeCartItemsAdapter.updateList(it)

            val linearLayoutManager = LinearLayoutManager(this)

            productsRv.apply {
                adapter = storeCartItemsAdapter
                layoutManager = linearLayoutManager
            }
        })

        storeViewModel.closeActivity.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }
}