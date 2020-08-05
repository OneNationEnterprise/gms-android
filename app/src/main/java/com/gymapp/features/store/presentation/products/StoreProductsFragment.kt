package com.gymapp.features.store.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseFragment
import com.gymapp.features.store.domain.products.products.StoreProductsViewModel
import com.gymapp.features.store.presentation.homepage.StoreActivity
import com.gymapp.helper.Constants
import kotlinx.android.synthetic.main.fragment_store.progressBar
import kotlinx.android.synthetic.main.fragment_store_products.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class StoreProductsFragment : BaseFragment() {

    private var gridLayoutManager: GridLayoutManager? = null

    lateinit var storeProductsViewModel: StoreProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments ?: return

        storeProductsViewModel = getViewModel()

        setTitle(arguments?.getString(Constants.pageTitle)!!)

        bindViewModelObservers()

        showLoading()

        GlobalScope.launch {
            storeProductsViewModel.getProducts(
                arguments?.getString(Constants.brandId),
                arguments?.getString(Constants.categoryId)
            )
        }

    }

    private fun bindViewModelObservers() {
        storeProductsViewModel.products.observe(viewLifecycleOwner, Observer {

            hideLoading()

            val productAdapterItem = ProductsAdapter(it, activity as StoreActivity)

            gridLayoutManager = GridLayoutManager(context, 4)

            gridLayoutManager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return 2
                }
            }

            productsRv.apply {
                adapter = productAdapterItem
                layoutManager = gridLayoutManager
            }

//            setupRecycleViewTabLayoutSync(pairList)
        })
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun setupRecycleViewTabLayoutSync(pairList: MutableList<Pair<Int, Int>>) {
        //products scroll listener
        /*    productsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        isUserScrolling = true
                    } else isTabSelectedFromScroll = false
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val itemPosition = gridLayoutManager?.findFirstCompletelyVisibleItemPosition()

                    if (itemPosition != null && isUserScrolling && categoriesTabs != null) {
                        val pair = getNearestNumber(pairList, itemPosition)
                        val tab = categoriesTabs.getTabAt(pair)
                        isTabSelectedFromScroll = true
                        if (tab != null) {
                            tab.select()
                        }
                    }
                }
            })

            //menu sections selected listener
            categoriesTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    if (isTabSelectedFromScroll) {
                        isTabSelectedFromScroll = false
                        return
                    }
                    isUserScrolling = false
                    val position = tab.position
                    val pair = pairList.find { it.first == position }
                    if (pair != null) {
                        gridLayoutManager?.scrollToPositionWithOffset(pair.second, 0)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })*/
    }
}