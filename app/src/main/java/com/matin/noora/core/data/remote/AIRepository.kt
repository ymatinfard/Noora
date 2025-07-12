package com.matin.noora.core.data.remote

import com.matin.noora.core.common.Result
import com.matin.noora.core.domain.AIRepository
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse

class AIRepositoryImpl(private val genAIApi: GenAIApi) : AIRepository {
    override suspend fun getTextResponse(prompt: PromptRequest): Result<TextAIResponse> {
        return try {
            val networkPrompt = PromptNetwork(
                prompt = prompt.rawPrompt,
                category = prompt.category.name,
                userId = "123",
                sessionId = "321"
            )
            val result = genAIApi.getTextAIResponse(networkPrompt)
            Result.Success(result.toDomain())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}