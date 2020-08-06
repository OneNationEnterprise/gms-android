package com.gymapp.main

import android.app.Application
import com.amitshekhar.DebugDB
import com.cloudinary.android.MediaManager
import com.cloudinary.android.signed.Signature
import com.cloudinary.android.signed.SignatureProvider
import com.google.firebase.FirebaseApp
import com.gymapp.main.di.ApplicationModule.repositoryModule
//import com.gymapp.main.di.ApplicationModule.databaseModule
import com.gymapp.main.di.ApplicationModule.networkModule
import com.gymapp.main.di.ApplicationModule.viewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GymApplication : Application() {


    companion object {
        lateinit var instance: GymApplication
    }


    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        instance = this

        AndroidThreeTen.init(this)

        DebugDB.getAddressLog()
        startKoin {
            androidContext(this@GymApplication)
            modules(
                networkModule,
//                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }

        initCloudinary()
    }

    private fun initCloudinary() {
        val cloudinaryConfig = HashMap<String, String>()
        cloudinaryConfig["cloud_name"] = "gymat"
        MediaManager.init(this, object : SignatureProvider {

            override fun getName(): String {
                return "SampleSignatureProvider" // for logging purposes
            }

            override fun provideSignature(options: MutableMap<Any?, Any?>?): Signature {

                val paramsToSign = java.util.HashMap<String, Any>()

                for (option in options!!) {
                    paramsToSign[option.key.toString()] = option.value!!
                }
                val sorted = paramsToSign.toSortedMap()

                val signature = MediaManager.get().cloudinary.apiSignRequest(sorted, "TGnQnXfr6nerHnBN8PsjzISQztM")

                return Signature(signature, "655386239947849", options["timestamp"].toString().toLong())
            }
        }, cloudinaryConfig)
    }

    override fun onTerminate() {
        super.onTerminate()
    }


}