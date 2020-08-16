package com.gymapp.features.store.presentation.cart

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.onboarding.auth.presentation.AuthLoginDialogFragment
import com.gymapp.features.store.domain.cart.StoreCartViewModel
import com.gymapp.features.store.presentation.address.StoreSelectAddressActivity
import com.gymapp.features.store.presentation.cart.adapter.StoreCartItemAdapter
import com.gymapp.features.store.presentation.cart.adapter.item.ProductItem
import com.gymapp.helper.Constants
import com.gymapp.helper.LOGIN_PATH
import com.gymapp.helper.STORE_CART_PRODUCTS
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

            if (FirebaseAuth.getInstance().currentUser == null) {
                AuthLoginDialogFragment.newInstance(LOGIN_PATH.STORE_CHECKOUT)
                    .show(supportFragmentManager, AuthLoginDialogFragment.TAG)
                return@setOnClickListener
            }

            storeViewModel.openSelectAddress()
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

        storeViewModel.openSelectAddress.observe(this, Observer {
            val intent = Intent(this, StoreSelectAddressActivity::class.java)
            val args = Bundle()

            val prductsList = mutableListOf<ProductItem>()

            for(product in it){
                if(product.getType() == STORE_CART_PRODUCTS.PRODUCT_ROW.ordinal){
                    prductsList.add(product as ProductItem)
                }
            }

            args.putString(Constants.openSelectAddress, Gson().toJson(prductsList))
            intent.putExtra(Constants.arguments, args)

            startActivity(intent)
        })
    }
}