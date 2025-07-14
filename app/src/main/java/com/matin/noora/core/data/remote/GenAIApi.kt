package com.matin.noora.core.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface GenAIApi {
    @POST("/v1/engine/text")
    suspend fun getTextAIResponse(@Body prompt: PromptNetwork): TextAINetwork

    @POST("/v1/engine/characters")
    suspend fun getCharacters(): CharacterListNetwork
}