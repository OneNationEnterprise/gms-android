package com.gymapp.features.profile.edit.presentation.changename

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.edit.domain.ChangeCustomerNameVIewModel
import com.gymapp.helper.Constants
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_change_user_name.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ChangeCustomerNameActivity : BaseActivity(R.layout.activity_change_user_name),
    ChangeCustomerNameView {

    var changeCustomerNameVM: ChangeCustomerNameVIewModel = getViewModel()

    private var isFirstName = true

    override fun bindViewModelObservers() {}

    override fun setupViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(getString(R.string.edit_user_profile))

        changeCustomerNameVM.listener = this

        editTextValue.doAfterTextChanged {
            if (it!!.length > 1) {
                saveValue.setBackgroundResource(R.drawable.bg_round_red)
                saveValue.setOnClickListener {
                    saveValueClick()
                }
            } else {
                saveValue.setBackgroundResource(R.drawable.save_button_inactive)
                saveValue.setOnClickListener { }
            }
            hideErrors()
        }
        saveValue.setOnClickListener {
            saveValueClick()
        }

        val bundle = intent.getBundleExtra(Constants.arguments) ?: return

        isFirstName = bundle.getBoolean(Constants.isFirstName)

        changeCustomerNameVM.fetchData(isFirstName)

        if (!isFirstName) {
            inputValue.hint = getString(R.string.edt_hint_last_name)
        }
    }

    private fun saveValueClick() {

        GlobalScope.launch {
            if (isFirstName) {
                changeCustomerNameVM.saveFirstName(editTextValue.text.toString())
            } else {
                changeCustomerNameVM.saveLastName(editTextValue.text.toString())
            }
        }
    }

    override fun setValue(customerName: String) {
        editTextValue.setText(customerName)
    }

    override fun showNotValidFirstName() {
        inputValue.error = getString(R.string.error_firstname_wrong_validation)
    }

    override fun showNotValidLastName() {
        inputValue.error = getString(R.string.error_lastname_wrong_validation)
    }

    private fun hideErrors() {
        inputValue.isErrorEnabled = false
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showErrorBanner(textMessage: String?) {
        progressBar.visibility = View.GONE
        InAppBannerNotification.showErrorNotification(changeNameContainer, this, textMessage)
    }

    override fun changesSavedSuccesfully() {
        setResult(RESULT_OK)
        finish()
    }
}