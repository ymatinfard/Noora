package com.matin.noora.core.domain

import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse

interface AIRepository {
    suspend fun getTextResponse(prompt: PromptRequest): TextAIResponse
}