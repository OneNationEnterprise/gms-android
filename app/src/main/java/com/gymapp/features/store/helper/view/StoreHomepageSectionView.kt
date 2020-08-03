package com.gymapp.features.store.helper.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.features.store.data.HomepageSection
import com.gymapp.features.store.data.model.Store
import com.gymapp.features.store.presentation.homepage.StoreItemSelectedListener
import com.gymapp.features.store.presentation.homepage.adapter.StoreHomepageAdapter
import kotlinx.android.synthetic.main.store_homepage_horizontal_carousel.view.*

class StoreHomepageSectionView(
    context: Context,
    section: List<HomepageSection>,
    listener: StoreItemSelectedListener
) : ConstraintLayout(context) {
    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.store_homepage_horizontal_carousel, this, true)

        if (section[0].getHeaderTitle().isEmpty()) {
            headerTitleTv.visibility = View.GONE
            seeAllTv.visibility = View.GONE
        } else {
            headerTitleTv.text = section[0].getHeaderTitle()
        }
        if (section[0] is Store) {
            seeAllTv.visibility = View.VISIBLE
            seeAllTv.setOnClickListener {
                listener.hasSelectedSeeAllStores()
            }
        }

        val optionSetAdapter = StoreHomepageAdapter(section)

        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        itemsRv.apply {
            adapter = optionSetAdapter
            layoutManager = linearLayoutManager
        }
    }
}