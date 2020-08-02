package com.gymapp.features.store.helper.view

import android.content.Context
import android.content.Context.*
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.gymapp.R

class StoreHomepageEmptyView(context: Context) : LinearLayout(context) {

    init {
        val inflater = context
                .getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_padding_bottom, this, true)
    }
}