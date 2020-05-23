package com.gymapp.main.network.interceptors

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.ExecutionException

class AuthHeaderTokenInterceptor : Interceptor {

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        var request: Request = chain.request()

        synchronized(this) {
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser != null) {
                try {

                    val task: Task<GetTokenResult> = currentUser.getIdToken(true)
                    val tokenResult = Tasks.await(task)
                    val sessionToken = tokenResult.token

                    request = request
                        .newBuilder()
                        .header(AUTHORIZATION, "$BEARER $sessionToken")
                        .build()

                } catch (e: ExecutionException) {
//                    FirebaseAnalyticsHelper.logEvent("ExecutionException: " + e.localizedMessage)
                }
            }

            return chain.proceed(request)
        }
    }
}