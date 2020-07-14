package com.gymapp.main

import android.app.Application
import com.google.firebase.FirebaseApp
import com.gymapp.main.di.ApplicationModule
import com.gymapp.main.di.ApplicationModule.repositoryModule
import com.gymapp.main.di.ApplicationModule.databaseModule
import com.gymapp.main.di.ApplicationModule.networkModule
import com.gymapp.main.di.ApplicationModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GymApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@GymApplication)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}