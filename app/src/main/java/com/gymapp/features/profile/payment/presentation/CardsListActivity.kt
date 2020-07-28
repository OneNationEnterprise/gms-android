package com.gymapp.features.profile.payment.presentation

import android.content.Intent
import android.os.Bundle
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_profile_cards_list.*

class CardsListActivity : BaseActivity(R.layout.activity_profile_cards_list) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.profile_payment))

        addNewCardTv.setOnClickListener {
            startActivity(Intent(this, SaveCardActivity::class.java))
        }
    }


    override fun setupViewModel() {}

    override fun bindViewModelObservers() {}


}