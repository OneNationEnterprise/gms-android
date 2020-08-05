package com.gymapp.features.store.presentation.brand

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseFragment
import com.gymapp.features.store.domain.products.brandList.StoreBrandListViewModel
import com.gymapp.features.store.presentation.homepage.StoreActivity
import com.gymapp.helper.rv.GridItemSpacingDecoration
import kotlinx.android.synthetic.main.fragment_store_brand_list.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class StoreBrandListFragment : BaseFragment() {

    private lateinit var storeBrandListAdapter: StoreBrandListAdapter
    private lateinit var storeBrandListVM: StoreBrandListViewModel

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

        storeBrandListVM = getViewModel()

        brandsSearchEt.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    storeBrandListVM.filterBrandsList(s.toString())
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

        initAdapter()

        bindViewModelObserver()

        storeBrandListVM.fetchData()
    }

    private fun bindViewModelObserver() {
        storeBrandListVM.filteredBrandsList.observe(viewLifecycleOwner, Observer {
            storeBrandListAdapter.updateList(it)
        })
    }


    private fun initAdapter() {
        val spanCount = resources.getInteger(R.integer.brand_list_grid_span_size)
        val gridSpacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)

        val gridLayoutManager = GridLayoutManager(activity, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }

        brandsRv.addItemDecoration(GridItemSpacingDecoration(spanCount, gridSpacing, true))

        storeBrandListAdapter = StoreBrandListAdapter(ArrayList(), activity as StoreActivity)

        brandsRv.adapter = storeBrandListAdapter
        brandsRv.layoutManager = gridLayoutManager
    }



}