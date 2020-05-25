package com.gymapp.features.onboarding.auth.domain

import android.telephony.PhoneStateListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.gym.type.RegisterCustomerInput
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.helper.modal.phoneprefix.PhonePrefixSelectedListener
import com.gymapp.main.data.model.country.Country
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepositoryInterface,
    private val authInteractorInterface: AuthInteractorInterface
) : BaseViewModel() {

    private var countriesList: LiveData<List<Country>>? = null

    var showOtpView = MutableLiveData<Boolean>()
    var showPhoneNumberError = MutableLiveData<Boolean>()
    var otpFailedVerificationMessage = MutableLiveData<String>()
    var showRegisterWithEmail = MutableLiveData<Boolean>()
    var authErrorMessage = MutableLiveData<String?>()

    private lateinit var resendAuthToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneVerificationId: String

    private var phoneVerifyCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var authResultListener: OnCompleteListener<AuthResult>

    var phoneCountry: Country? = null
    private var phoneNumber: String = ""

    init {
        countriesList = repository.getCountries()

        phoneVerifyCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                //todo do next based on login/register
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showPhoneNumberError.value = true
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                showOtpView.value = true

                phoneVerificationId = p0
                resendAuthToken = p1
            }
        }

        authResultListener = OnCompleteListener<AuthResult> {

            if (it.isSuccessful) {
                val firebaseUser = it.result?.user

                if (!firebaseUser?.email.isNullOrEmpty()) {
                    //already registered --> cache customer details from server
                    viewModelScope.launch {
                        authErrorMessage.value =
                            repository.saveUserDetailsByEmail(firebaseUser!!.email!!)
                    }
                    return@OnCompleteListener
                }

                showRegisterWithEmail.value = true

            } else {
                //error verification code
                otpFailedVerificationMessage.value = it.exception?.localizedMessage
            }
        }

    }

    fun getAvailableCountries(): LiveData<List<Country>>? {
        return countriesList
    }

    fun validatePhoneNumber(phoneNumber: String, activity: BaseActivity) {
        this.phoneNumber = phoneNumber
        authInteractorInterface.verifyPhoneNumber(
            phoneNumber,
            phoneCountry!!,
            activity,
            phoneVerifyCallback
        )
    }

    fun validateOtp(otpPin: String) {
        authInteractorInterface.signInWithCredential(
            PhoneAuthProvider.getCredential(
                phoneVerificationId,
                otpPin
            ),
            authResultListener
        )
    }

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            authErrorMessage.value = repository.registerUser(
                RegisterCustomerInput(
                    firstName = name,
                    email = email,
                    password = password,
                    contactNumber = phoneNumber,
                    countryId = phoneCountry!!.id,
                    isPhoneVerified = true
                )
            )
        }
    }

}

