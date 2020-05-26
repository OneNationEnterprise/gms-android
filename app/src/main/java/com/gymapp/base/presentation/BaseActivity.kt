package com.gymapp.base.presentation

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    abstract fun setupViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()

        setupCustomBackBtn()
    }

    private fun setupCustomBackBtn() {
        if (backIv != null) {
            backIv.setOnClickListener {
                onBackPressed()
            }
        }
    }

    fun setCustomToolbarTitle(title: String) {
        if (toolbarTitleTv != null) {
            toolbarTitleTv.text = title
        }
    }

}
