package com.gymapp.features.profile.edit.presentation.password

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.edit.domain.UpdatePasswordViewModel
import com.gymapp.helper.Constants
import com.gymapp.helper.extensions.isValidPassword
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_update_password.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class UpdatePasswordActivity : BaseActivity(R.layout.activity_update_password), UpdatePasswordView {

    lateinit var updatePasswordViewModel: UpdatePasswordViewModel
    lateinit var activity: BaseActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updatePasswordViewModel.listener = this

        val bundle = intent.getBundleExtra(Constants.arguments) ?: return
        val email = bundle.getString(Constants.email) ?: return

        activity = this

        updateTv.setOnClickListener {
            val oldPassword = editTextOldPassword.text.toString()
            val newPassword = editTextNewPassword.text.toString()

            if (newPassword.isValidPassword()) {
                val instance = FirebaseAuth.getInstance()

                progressBar.visibility = View.VISIBLE

                instance.signInWithEmailAndPassword(email, oldPassword).addOnSuccessListener {

                    instance.currentUser?.updatePassword(newPassword)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            GlobalScope.launch {
                                updatePasswordViewModel.updatePassword(newPassword)
                            }
                        } else {
                            showErrorBanner(it.exception?.localizedMessage)
                        }
                    }

                }.addOnFailureListener {
                    showErrorBanner(it.localizedMessage)
                }.addOnFailureListener(FirebaseFailureListener())
            } else {
                showErrorBanner(getString(R.string.error_password_wrong_validation))
            }
        }
    }

    override fun setupViewModel() {
        updatePasswordViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {
    }

    override fun showErrorBanner(message: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.GONE
            InAppBannerNotification.showErrorNotification(updatePasswordContainer, activity, message)
        }
    }

    override fun passwordSuccessUpdated() {
        CoroutineScope(Dispatchers.Main).launch {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    internal inner class FirebaseFailureListener : OnFailureListener {
        override fun onFailure(exception: Exception) {
            try {
                showErrorBanner((exception as FirebaseAuthException).localizedMessage)
            } catch (e: ClassCastException) {
                showErrorBanner(exception.localizedMessage)
            }
        }
    }

}