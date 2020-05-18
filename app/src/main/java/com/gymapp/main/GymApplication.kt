package com.gymapp.main

import android.app.Application
import com.gymapp.main.di.ApplicationModule
import com.gymapp.main.di.ApplicationModule.dataModule
import com.gymapp.main.di.ApplicationModule.databaseModule
import com.gymapp.main.di.ApplicationModule.networkModule
import com.gymapp.main.di.ApplicationModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GymApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GymApplication)
            modules(
                networkModule,
                databaseModule,
                dataModule,
                viewModelModule
            )
        }
    }
}