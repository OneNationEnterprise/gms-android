package com.gymapp.features.store.presentation.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseFragment
import com.gymapp.features.store.domain.products.StoreProductsViewModel
import com.gymapp.features.store.helper.view.StoreHomepageEmptyView
import com.gymapp.features.store.helper.view.StoreHomepageSectionView
import kotlinx.android.synthetic.main.fragment_store.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class StoreFragment : BaseFragment() {

    lateinit var storeProductsViewModel: StoreProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.store_title_name))

        storeProductsViewModel = getViewModel()

        showLoading()

        bindViewModelObserver()

        GlobalScope.launch {
            storeProductsViewModel.getHomeFeed()
        }
    }

    private fun bindViewModelObserver() {
        storeProductsViewModel.homeData.observe(viewLifecycleOwner, Observer {

            homepageContainer.removeAllViews()

            if (it == null) {
                //todo show error
                return@Observer
            }

            if (!it.images.isNullOrEmpty()) {
                val storeHomePageView = StoreHomepageSectionView(requireContext(), it.images, activity as StoreActivity)
                homepageContainer.addView(storeHomePageView)
            }
            if (!it.stores.isNullOrEmpty()) {
                val storeHomePageView = StoreHomepageSectionView(requireContext(), it.stores, activity as StoreActivity)
                homepageContainer.addView(storeHomePageView)
            }
            if (!it.categories.isNullOrEmpty()) {
                val storeHomePageView = StoreHomepageSectionView(requireContext(), it.categories,activity as StoreActivity)
                homepageContainer.addView(storeHomePageView)
            }
            if (!it.bestSeller.isNullOrEmpty()) {
                val storeHomePageView = StoreHomepageSectionView(requireContext(), it.bestSeller, activity as StoreActivity)
                homepageContainer.addView(storeHomePageView)
            }

            homepageContainer.addView(StoreHomepageEmptyView(requireContext()))

            hideLoading()
        })
    }

    private fun showLoading() {
        wvProgressIndicator.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        wvProgressIndicator.visibility = View.GONE
    }

}