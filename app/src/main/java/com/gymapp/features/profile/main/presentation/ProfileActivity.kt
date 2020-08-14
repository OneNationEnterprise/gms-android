package com.gymapp.features.profile.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.addresses.presentation.list.AddressesListActivity
import com.gymapp.features.profile.edit.presentation.profile.EditProfileActivity
import com.gymapp.features.profile.main.domain.ProfileViewModel
import com.gymapp.features.profile.medical.domain.MedicalFormViewModel
import com.gymapp.features.profile.medical.presentation.MedicalFormActivity
import com.gymapp.features.profile.payment.presentation.CardsListActivity
import com.gymapp.features.profile.settings.presentation.SettingsActivity
import com.gymapp.features.profile.transaction.presentation.TransactionActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.user_icon.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProfileActivity : BaseActivity(R.layout.activity_profile) {

    lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCustomToolbarTitle(getString(R.string.profile))

        editTv.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        medicalContainer.setOnClickListener {
            startActivity(Intent(this, MedicalFormActivity::class.java))
        }

        addressesContainer.setOnClickListener {
            startActivity(Intent(this, AddressesListActivity::class.java))
        }

        paymentContainer.setOnClickListener {
            startActivity(Intent(this, CardsListActivity::class.java))
        }

        settingsContainer.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        transactionContainer.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }
    }

    override fun setupViewModel() {
        profileViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
        profileViewModel.user.observe(this, Observer {
            if (it != null) {
                userNameTv.text = it.fullName
                loggedInInitialsTextView.text = it.fullName[0].toString()
            }
        })
    }
}