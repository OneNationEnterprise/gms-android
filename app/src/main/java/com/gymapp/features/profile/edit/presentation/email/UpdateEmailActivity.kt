//package com.gymapp.features.profile.edit.presentation.email
//
//import android.app.Activity
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import com.google.firebase.auth.ActionCodeSettings
//import com.google.firebase.auth.EmailAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//import com.gymapp.R
//import com.gymapp.base.presentation.BaseActivity
//
//class UpdateEmailActivity : BaseActivity(R.layout.activity_update_email),UpdateEmailView{
//    override fun setupViewModel() {
//
//    }
//
//    override fun bindViewModelObservers() {
//        TODO("Not yet implemented")
//    }
//    private val updateEmailVM: UpdateEmailVM<UpdateEmailNavigator> by this.viewModel()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(getLayoutRes().layout)
//        updateEmailVM.attachNavigator(this)
//
//        val bundle = intent.getBundleExtra(Constants.arguments) ?: return
//        val oldEmail = bundle.getString(Constants.email) ?: return
//
//        toolbar?.let {
//            setSupportActionBar(it)
//            supportActionBar?.let { actionBar ->
//                actionBar.setDisplayShowHomeEnabled(true)
//                actionBar.title = ""
//                toolbarTitle.text = getString(R.string.edit_user_profile)
//                actionBar.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_back_auto_mirrored))
//            }
//        }
//
//        editTextOldEmail.setText(oldEmail)
//
//        updateTv.setOnClickListener {
//
//            if (editTextNewEmail.text.toString().isValidEmail() && editTextPassword.text.toString().isValidPassword()) {
//                progressBar.visibility = View.VISIBLE
//                updateEmail(oldEmail, editTextPassword.text.toString())
//            }
//        }
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        updateEmailVM.dettachNavigator()
//    }
//
//
//    override fun updateEmail(email: String, password: String) {
//        val user = FirebaseAuth.getInstance().currentUser
//
//        if (user == null) {
//            showError("Firebase user not found")
//            return
//        }
//        // Get auth credentials from the user for re-authentication
//        // Get auth credentials from the user for re-authentication
//        val credential = EmailAuthProvider
//            .getCredential(email, password) // Current Login Credentials \\
//
//        // Prompt the user to re-provide their sign-in credentials
//        // Prompt the user to re-provide their sign-in credentials
//        user.reauthenticate(credential)
//            .addOnCompleteListener {
//
//                if (it.isSuccessful) {
//                    //Now change your email address \\
//                    //----------------Code for Changing Email Address----------\\
//                    val user = FirebaseAuth.getInstance().currentUser
//
//                    if (user == null) {
//                        showError("Firebase user not found after reauthentication")
//                        return@addOnCompleteListener
//                    }
//                    user.updateEmail(editTextNewEmail.text.toString())
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                updateEmailVM.updateEmailOnCOFEServer(editTextNewEmail.text.toString())
//                                verifyEmailAfterUpdate(editTextNewEmail.text.toString())
//                            } else {
//                                showError(task.exception?.localizedMessage)
//                            }
//                        }
//                    //----------------------------------------------------------\\
//                } else {
//                    showError(it.exception?.localizedMessage)
//                }
//
//            }
//    }
//
//    override fun showError(error: String?) {
//        progressBar.visibility = View.GONE
//        InAppBannerNotification.showErrorNotification(verifyEmailContainer, this, error)
//
//    }
//
//    private fun verifyEmailAfterUpdate(email: String) {
//        val actionCodeSettings = ActionCodeSettings.newBuilder()
//            .setUrl("https://cofeapp.com/applinks/verify/?email=$email")
//            .setHandleCodeInApp(true)
//            .setDynamicLinkDomain(BuildConfig.FIREBASE_DEEPLINK_URI.removePrefix("https://"))
//            .setIOSBundleId(BuildConfig.IOS_BUNDLE_ID)
//            .setAndroidPackageName(BuildConfig.APPLICATION_ID, false, null)
//            .build()
//
//        FirebaseAuth.getInstance().currentUser?.sendEmailVerification(actionCodeSettings)?.addOnCompleteListener {
//            if (it.isSuccessful) {
//                progressBar.visibility = View.GONE
//                Toast.makeText(this, "A verification email has been sent", Toast.LENGTH_SHORT).show()
//                setResult(Activity.RESULT_OK)
//                Thread.sleep(1000)
//                finish()
//
//            } else {
//                progressBar.visibility = View.GONE
//                InAppBannerNotification.showErrorNotification(verifyEmailContainer, this, it.exception?.localizedMessage)
//            }
//        }
//    }
//}
//
//}