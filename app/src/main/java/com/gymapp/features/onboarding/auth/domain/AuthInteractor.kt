package com.gymapp.features.onboarding.auth.domain

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.main.data.model.country.Country
import java.util.concurrent.TimeUnit

class AuthInteractor : AuthInteractorInterface {

    private val RESEND_OTP_TIMER = 60000

    override fun verifyPhoneNumber(
        phoneNumber: String,
        phoneCountry: Country,
        activity: BaseActivity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "${phoneCountry.dialCode}$phoneNumber", // Phone number to verify
            RESEND_OTP_TIMER.toLong(), // Timeout duration
            TimeUnit.MILLISECONDS, // Unit of timeout
            activity, // Activity (for callback binding)
            callbacks,
            token)
    }

    override fun signInWithCredential(
        credential: AuthCredential,
        listener: OnCompleteListener<AuthResult>
    ) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(listener)
    }
}

