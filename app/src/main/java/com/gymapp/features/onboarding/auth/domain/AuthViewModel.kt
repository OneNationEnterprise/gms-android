package com.gymapp.features.onboarding.auth.domain

import android.os.CountDownTimer
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
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.main.data.model.country.Country
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepositoryInterface,
    private val configRepository: ConfigRepositoryInterface,
    private val authInteractorInterface: AuthInteractorInterface
) : BaseViewModel() {

    private var countriesList = MutableLiveData<List<Country>>()

    var showOtpView = MutableLiveData<Boolean>()
    var showPhoneNumberError = MutableLiveData<Boolean>()
    var otpFailedVerificationMessage = MutableLiveData<String>()
    var showRegisterWithEmail = MutableLiveData<Boolean>()
    var authErrorMessage = MutableLiveData<String?>()
    var enableResendOtpButton = MutableLiveData<Boolean>()

    private var resendAuthToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var phoneVerificationId: String

    private var phoneVerifyCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var resendOtpCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var authResultListener: OnCompleteListener<AuthResult>

    var phoneCountry: Country? = null
    private var phoneNumber: String = ""

    init {
        countriesList?.value = configRepository.getCountries()

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

        resendOtpCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                //todo do next based on login/register
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                //show error on resending otp
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
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
                            userRepository.saveUserDetailsByEmail(firebaseUser!!.email!!)
                    }
                    return@OnCompleteListener
                }

                showRegisterWithEmail.value = true

            } else {
                //error verification code
                otpFailedVerificationMessage.value = it.exception?.localizedMessage
            }
        }

        resendOtpVerificationTimer()
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
            phoneVerifyCallback,
            resendAuthToken
        )
    }

    fun resendOtp(activity: BaseActivity) {
        authInteractorInterface.verifyPhoneNumber(
            phoneNumber,
            phoneCountry!!,
            activity,
            resendOtpCallback,
            resendAuthToken
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
            authErrorMessage.value = userRepository.registerUser(
                RegisterCustomerInput(
                    firstName = name,
                    lastName = "todo",
                    email = email,
                    password = password,
                    contactNumber = "${phoneCountry?.dialCode}$phoneNumber",
                    countryId = phoneCountry!!.countryId,
                    isPhoneVerified = true
                )
            )
        }
    }


    fun resendOtpVerificationTimer() {
        enableResendOtpButton.value = false
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                enableResendOtpButton.value = true
            }
        }.start()
    }

    fun loginWithEmailAndPassword(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val firebaseUser = it.result?.user

                    if (firebaseUser == null || firebaseUser.email == null) {
                        authErrorMessage.value = "No user information from firebase"
                        return@addOnCompleteListener
                    }

                    viewModelScope.launch {
                        authErrorMessage.value =
                            userRepository.saveUserDetailsByEmail(firebaseUser!!.email!!)
                    }
                } else {
                    authErrorMessage.value = it.exception?.localizedMessage
                }
            }
    }


}

