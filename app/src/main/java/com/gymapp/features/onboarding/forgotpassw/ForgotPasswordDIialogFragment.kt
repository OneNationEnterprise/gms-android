package com.gymapp.features.onboarding.forgotpassw

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.gymapp.R
import com.gymapp.base.presentation.BaseDialogFragment
import com.gymapp.helper.extensions.isValidEmail
import kotlinx.android.synthetic.main.dialog_forgot_password.*

class ForgotPasswordDialogFragment : BaseDialogFragment(R.layout.dialog_forgot_password) {

    companion object {
        const val TAG = "ForgotPasswordDialogFragment"

        fun newInstance(): ForgotPasswordDialogFragment {
            return ForgotPasswordDialogFragment()
        }
    }

    override fun bindView(savedInstanceState: Bundle?) {
        verifyEmailBtn.setOnClickListener {
            if (forgotPassEditTextEmail.text.toString().isValidEmail()) {

                progressBar.visibility = View.VISIBLE

                FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(forgotPassEditTextEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            setEmailSentView(forgotPassEditTextEmail.text.toString())
                        } else {
                            errorMessageTv.visibility = View.VISIBLE
                            errorMessageTv.text = task.exception?.localizedMessage
                        }

                        progressBar.visibility = View.GONE
                    }
            } else {
                errorMessageTv.visibility = View.VISIBLE
                errorMessageTv.text = "Invalid email"
            }
        }

        forgotPassEditTextEmail.doAfterTextChanged {
            errorMessageTv.visibility = View.GONE
        }

        closeIv.setOnClickListener {
            dismiss()
        }
    }

    fun setEmailSentView(email: String) {
        forgotPasswordTitleTextTv.visibility = View.GONE
        loginInputEmail.visibility = View.GONE
        verifyEmailBtn.visibility = View.GONE

        emailResentTv.text = getString(R.string.forgot_password_resent_text, email)

        emailResentIv.visibility = View.VISIBLE
        emailResentTv.visibility = View.VISIBLE
        openEmailApp.visibility = View.VISIBLE

        openEmailApp.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            context?.startActivity(intent)
        }
    }
}