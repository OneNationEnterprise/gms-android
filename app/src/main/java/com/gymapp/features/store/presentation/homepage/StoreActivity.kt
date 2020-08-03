package com.gymapp.features.store.presentation.homepage

import android.os.Bundle
import androidx.navigation.findNavController
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.helper.Constants

class StoreActivity : BaseActivity(R.layout.activity_store), StoreItemSelectedListener {

    override fun setupViewModel() {}

    override fun bindViewModelObservers() {}

    override fun hasSelectedSeeAllStores() {

        findNavController(R.id.fragmentContainer).navigate(
            R.id.action_store_fragment_to_brands_list_fragment,
            null,
            null
        )
    }

    override fun hasSelectedSpecificStore(storeId: String, pageTitle:String) {
        val bundle = Bundle()
        bundle.putString(Constants.brandId, storeId)
        bundle.putString(Constants.pageTitle, pageTitle)

        findNavController(R.id.fragmentContainer).navigate(R.id.action_brands_list_fragment_to_storeProductsFragment, bundle, null)
    }


    override fun onResume() {
        super.onResume()
//        storeViewModel.fetchData()
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.fragmentContainer).popBackStack()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        storeViewModel.invalidateDataCache()
    }
}