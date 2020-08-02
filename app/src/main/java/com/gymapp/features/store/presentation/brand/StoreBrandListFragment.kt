package com.gymapp.features.store.presentation.brand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gymapp.R
import com.gymapp.base.presentation.BaseFragment

class StoreBrandListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_brand_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.store_title_name))
    }

/*
*     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeBrandListVM.attachNavigator(this)

        val brandsList = Gson().fromJson<MutableList<StoreFeed.AsBrand>>(arguments?.getString(Constants.brandsList),
                object : TypeToken<MutableList<StoreFeed.AsBrand>>() {}.type)

        storeBrandListVM.setBrandsList(brandsList)

        initToolbar(getString(R.string.store_title), false)

        brandsSearchEt.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        storeBrandListVM.filterBrandsList(s.toString())
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                })
    }

    override fun updateUI(brandsList: MutableList<StoreFeed.AsBrand>) {
        val spanCount = resources.getInteger(R.integer.brand_list_grid_span_size)
        val gridSpacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)

        val gridLayoutManager = GridLayoutManager(activity, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }

        brandsRv.addItemDecoration(GridItemSpacingDecoration(spanCount, gridSpacing, true))

        storeBrandListAdapter = StoreBrandsListAdapter(brandsList, storeActivityListener)

        brandsRv.adapter = storeBrandListAdapter
        brandsRv.layoutManager = gridLayoutManager
    }

    override fun updateBrandsList(brandsList: MutableList<StoreFeed.AsBrand>) {
        storeBrandListAdapter.updateList(brandsList)
    }
*/

}