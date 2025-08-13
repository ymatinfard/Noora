package com.matin.noora.core.data.remote

import com.matin.noora.core.data.local.NooraDataStorePref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val dataStore: NooraDataStorePref): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${dataStore.getTokenImmediately()}").build()

        return chain.proceed(request)
    }
}