package com.gymapp.base.presentation

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    abstract fun setupViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()

        setupCustomBackBtn()

        bindViewModelObservers()
    }

    abstract fun bindViewModelObservers()

    private fun setupCustomBackBtn() {
        /**
         * todo delete this backIv
         */
        if (backIv != null) {
            backIv.setOnClickListener {
                onBackPressed()
            }
            return
        }
        if (backArrowIv != null) {
            backArrowIv.setOnClickListener {
                onBackPressed()
            }
            return
        }
    }

    /**
     * todo delete this
     */
    fun setCustomToolbarTitle(title: String) {
        if (toolbarTitleTv != null) {
            toolbarTitleTv.text = title
        }
    }

    fun setTitle(title: String) {
        if (titleNameTv != null) {
            titleNameTv.text = title
        }
    }

}
