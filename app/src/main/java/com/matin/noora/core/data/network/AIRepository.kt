package com.matin.noora.core.data.network

import com.matin.noora.core.domain.AIRepository
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse

class AIRepositoryImpl: AIRepository {
    override suspend fun getTextResponse(prompt: PromptRequest): TextAIResponse {
       return TextAIResponse(text = "AI Response")
    }
}