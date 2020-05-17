package com.gymapp.main.di

import com.gymapp.main.network.ApiManagerImpl
import com.gymapp.main.network.ApiManagerInterface
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

object ApplicationModule {
    val modules = listOf(
        module {

            single {
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    //TODO add before production push
//                    .certificatePinner(
//                        CertificatePinner.Builder()
//                            .add(
//                                "DOMAIN",
//                                "SHA256"
//                            )
//                            .build()
//                    )
                    .build()
            }

            single<ApiManagerInterface> { ApiManagerImpl(get()) }

        })
}


