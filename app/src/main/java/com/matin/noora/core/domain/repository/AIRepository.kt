package com.matin.noora.core.domain.repository

import com.matin.noora.core.common.Result
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse
import kotlinx.coroutines.flow.Flow

interface AIRepository {
    suspend fun getTextResponse(prompt: PromptRequest): Result<TextAIResponse>
    fun getChatCharacters(): Flow<List<ChatCharacterItem>>
}