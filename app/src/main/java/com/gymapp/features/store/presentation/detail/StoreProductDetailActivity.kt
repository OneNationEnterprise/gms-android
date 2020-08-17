package com.gymapp.features.store.presentation.detail

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import com.apollographql.apollo.gym.type.GlobalStatusType
import com.apollographql.apollo.gym.type.StatusType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.store.data.model.Product
import com.gymapp.helper.Constants
import com.gymapp.helper.view.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_store_product_detail.*

class StoreProductDetailActivity : BaseActivity(R.layout.activity_store_product_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent?.getBundleExtra(Constants.arguments) ?: return

        initView(Gson().fromJson<Product>(bundle.getString(Constants.product),
            object : TypeToken<Product>() {}.type
        )
        )

        closeIv.setOnClickListener {
            finish()
        }
    }

    private fun initView(product: Product) {

        productName.text = product.name

        productPrice.text = product.salePrice.toString()

        oldProductPrice.text = product.listPrice.toString()
        oldProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        descriptionDetails.text = product.description

        brandNameTv.text = product.brand?.name

        if (!product.images.isNullOrEmpty()) {
            initViewPager(product.images)
        }

        if (product.status == GlobalStatusType.ACTIVE) {
            availabilityDescription.text = getString(R.string.store_in_stock)

            addProduct.setOnClickListener {
                addProductToCart(product)
            }

        } else {
            infoContainer.visibility = View.GONE
            outOfStockTv.visibility = View.VISIBLE
            addProduct.background = getDrawable(R.drawable.save_button_inactive)
        }

        if (product.warrantyDescription != null) {
            warrantyContainer.visibility = View.VISIBLE
            warranyDescription.text = product.warrantyDescription
            warrantyDivider.visibility = View.VISIBLE
        }

        returnDescription.text = product.returnPolicyDescription
        if (!product.returnPolicy) {
            returnIv.setImageResource(R.drawable.ic_noreturns)
                returnDescription.text = getString(R.string.store_product_details_no_return)
        }

        stockTv.text = "${product.inventory?.sumBy { 
            it!!.quantity
        }} items"

    }

    private fun initViewPager(images: List<String>) {

        val adapter = ProductImagesPagerAdapter(images)
        viewPager2.adapter = adapter

        val zoomOutPageTransformer = ZoomOutPageTransformer()
        viewPager2.setPageTransformer { page, position ->
            zoomOutPageTransformer.transformPage(page, position)
        }

        dotsIndicator.setViewPager2(viewPager2)
    }

    private fun addProductToCart(product: Product) {

        val bundle = Bundle()
        bundle.putString(Constants.product, Gson().toJson(product))

        val resultIntent = Intent()
        resultIntent.putExtra(Constants.arguments, bundle)

        setResult(RESULT_OK, resultIntent)
        finish()
    }


    override fun setupViewModel() {
    }

    override fun bindViewModelObservers() {
    }


}