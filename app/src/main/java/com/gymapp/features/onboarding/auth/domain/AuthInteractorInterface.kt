package com.gymapp.features.onboarding.auth.domain

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthProvider
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.main.data.model.country.Country

interface AuthInteractorInterface {
    fun verifyPhoneNumber(
        phoneNumber: String,
        phoneCountry: Country,
        activity: BaseActivity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        token: PhoneAuthProvider.ForceResendingToken?
    )

    /**
     * sign in with phone number; if phone number doesn't exist,
     * it create a new user with it
     */
    fun signInWithCredential(
        credential: AuthCredential,
        listener: OnCompleteListener<AuthResult>
    )
}