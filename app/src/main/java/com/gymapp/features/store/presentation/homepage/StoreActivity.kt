package com.gymapp.features.store.presentation.homepage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.transition.TransitionManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.store.data.model.Product
import com.gymapp.features.store.domain.products.products.StoreProductsViewModel
import com.gymapp.features.store.presentation.cart.StoreCartActivity
import com.gymapp.features.store.presentation.detail.StoreProductDetailActivity
import com.gymapp.helper.Constants
import kotlinx.android.synthetic.main.bottomsheet_store_cart_layout.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class StoreActivity : BaseActivity(R.layout.activity_store), StoreItemSelectedListener {

    private val REQUEST_OPEN_PRODUCT_DETAIL = 126
    private lateinit var storeProductsViewModel: StoreProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartLayout.setOnClickListener {
            startActivity(Intent(this, StoreCartActivity::class.java))
        }
    }

    override fun hasSelectedSeeAllStores() {

        findNavController(R.id.fragmentContainer).navigate(
            R.id.action_store_fragment_to_brands_list_fragment,
            null,
            null
        )
    }

    override fun hasSelectedSpecificStore(storeId: String, pageTitle: String) {
        val bundle = Bundle()
        bundle.putString(Constants.brandId, storeId)
        bundle.putString(Constants.pageTitle, pageTitle)

        findNavController(R.id.fragmentContainer).navigate(
            R.id.action_brands_list_fragment_to_storeProductsFragment,
            bundle,
            null
        )
    }

    override fun hasSelectedStore(storeId: String, pageTitle: String) {
        val bundle = Bundle()
        bundle.putString(Constants.brandId, storeId)
        bundle.putString(Constants.pageTitle, pageTitle)

        findNavController(R.id.fragmentContainer).navigate(
            R.id.action_store_fragment_to_allproducts_fragment,
            bundle,
            null
        )
    }

    override fun hasSelectedCategory(categoryId: String, categoryName: String) {
        val bundle = Bundle()
        bundle.putString(Constants.categoryId, categoryId)
        bundle.putString(Constants.pageTitle, categoryName)

        findNavController(R.id.fragmentContainer).navigate(
            R.id.action_store_fragment_to_allproducts_fragment,
            bundle,
            null
        )
    }

    override fun openProductDetail(product: Product) {

        val bundle = Bundle()
        bundle.putString(Constants.product, Gson().toJson(product))

        val intent = Intent(this, StoreProductDetailActivity::class.java)
        intent.putExtra(Constants.arguments, bundle)

        startActivityForResult(intent, REQUEST_OPEN_PRODUCT_DETAIL)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_OPEN_PRODUCT_DETAIL && data != null) {
            val bundle = data.getBundleExtra(Constants.arguments) ?: return

            val product = Gson().fromJson<Product>(
                bundle.getString(Constants.product),
                object : TypeToken<Product>() {}.type
            ) ?: return

            storeProductsViewModel.addProductToCart(product)
        }
    }

    override fun onResume() {
        super.onResume()
        storeProductsViewModel.fetchData()
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.fragmentContainer).popBackStack()) {
            super.onBackPressed()
        }
    }


    override fun setupViewModel() {
        storeProductsViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        storeProductsViewModel.showItemsInCart.observe(this, Observer {
            TransitionManager.beginDelayedTransition(cartContainer)
            cartContainer.visibility = View.VISIBLE

            itemsTv.text = getString(R.string.store_items_in_cart, it.first.toString())

            valueTv.text = it.second.toString()
        })

        storeProductsViewModel.hideCart.observe(this, Observer {
            TransitionManager.beginDelayedTransition(cartContainer)
            cartContainer.visibility = View.GONE
        })
    }
}