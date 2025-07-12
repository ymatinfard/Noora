package com.matin.noora.core.data.remote

import com.matin.noora.core.domain.AIRepository
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse

class AIRepositoryImpl(private val genAIApi: GenAIApi) : AIRepository {
    override suspend fun getTextResponse(prompt: PromptRequest): TextAIResponse {
        val networkPrompt = PromptNetwork(
            prompt = prompt.rawPrompt,
            category = prompt.category.name,
            userId = "123",
            sessionId = "321"
        )
        return genAIApi.getTextAIResponse(networkPrompt).toDomain()
    }
}