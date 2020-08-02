package com.gymapp.base.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gymapp.R
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.android.synthetic.main.toolbar_layout.*

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCustomBackBtn()
    }


    private fun setupCustomBackBtn() {
        /**
         * todo delete this backIv
         */
        if (backIv != null) {
            backIv.setOnClickListener {
                activity?.onBackPressed()
            }
            return
        }
        if (backArrowIv != null) {
            backArrowIv.setOnClickListener {
                activity?.onBackPressed()
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