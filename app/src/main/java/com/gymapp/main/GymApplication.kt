package com.gymapp.main

import android.app.Application
import com.amitshekhar.DebugDB
import com.google.firebase.FirebaseApp
import com.gymapp.main.di.ApplicationModule.repositoryModule
//import com.gymapp.main.di.ApplicationModule.databaseModule
import com.gymapp.main.di.ApplicationModule.networkModule
import com.gymapp.main.di.ApplicationModule.viewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GymApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

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
    }

    override fun onTerminate() {
        super.onTerminate()
    }



}