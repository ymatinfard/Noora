package com.matin.noora.core.data.remote

import com.matin.noora.core.data.remote.model.MessageRequestNetwork
import com.matin.noora.core.data.remote.model.MessageResponseNetwork
import retrofit2.http.Body
import retrofit2.http.POST

interface NooraApi {
    @POST("v1/engine/text")
    suspend fun sendMessage(@Body prompt: MessageRequestNetwork): MessageResponseNetwork

    @POST("v1/engine/characters")
    suspend fun getCharacters(): CharacterListNetwork

    @POST("v1/authorization/sign_in")
    suspend fun signIn(@Body auth: UserAuthNetworkRequest): UserAuthNetworkResponse

    @POST("v1/authorization/sign_up")
    suspend fun signUp(@Body auth: UserAuthNetworkRequest): UserAuthNetworkResponse
}