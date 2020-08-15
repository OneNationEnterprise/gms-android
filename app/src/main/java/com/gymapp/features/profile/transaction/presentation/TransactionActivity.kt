package com.gymapp.features.profile.transaction.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.transaction.domain.TransactionViewModel
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_profile_cards_list.*
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TransactionActivity : BaseActivity(R.layout.activity_transaction) {

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            transactionViewModel.getData()
        }
    }

    override fun setupViewModel() {
        transactionViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        transactionViewModel.error.observe(this, Observer {
            InAppBannerNotification.showErrorNotification(transactionContainer, this, it)
        })

        transactionViewModel.list.observe(this, Observer {

            transactionAdapter = TransactionAdapter(it)

            transactionsRv.apply {
                adapter = transactionAdapter
                layoutManager = LinearLayoutManager(context)
            }
        })
    }
}