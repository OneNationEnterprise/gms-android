package com.gymapp.features.store.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gymapp.R
import com.gymapp.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_store.*

class StoreProductsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** todo init title with Brand or Category name**/
        setTitle(getString(R.string.store_title_name))
    }

    fun showLoading() {
        wvProgressIndicator.visibility = View.VISIBLE
    }

    fun hideLoading() {
        wvProgressIndicator.visibility = View.GONE
    }


    /*
    *     override fun initView(category: Category.Category1) {

        initToolbar(LocalizedHelper.getLocalizedValue(context, category.name()), false)

        toolbarTitleDetails.text = getString(R.string.store_total_products, category.productsInCountry().size)

        val productsAdapterList = ArrayList<ProductAdapterItem>()

        for (product in category.productsInCountry()) {
            productsAdapterList.add(ProductAdapterItem(product.fragments().productFields()))
        }

        val productAdapterItem = ProductsAdapter(productsAdapterList,storeActivityListener)

        val gridLayoutManager = GridLayoutManager(context, 4)

        gridLayoutManager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (productAdapterItem.getItemViewType(position)) {
                    Enums.MENU.ITEM.ordinal -> 2
                    else -> 4
                }
            }
        }

        productsRv.apply {
            adapter = productAdapterItem
            layoutManager = gridLayoutManager
        }
    }
    * */
}