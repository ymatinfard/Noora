package com.matin.noora.core.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("v1/authorization/sign_in")
    suspend fun signIn(@Body auth: UserAuthNetworkRequest): UserAuthNetworkResponse

    @POST("v1/authorization/sign_up")
    suspend fun signUp(@Body auth: UserAuthNetworkRequest): UserAuthNetworkResponse
}