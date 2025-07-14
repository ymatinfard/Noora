package com.matin.noora.core.domain.repository

import com.matin.noora.core.common.Result
import com.matin.noora.core.domain.model.PromptRequest
import com.matin.noora.core.domain.model.TextAIResponse

interface AIRepository {
    suspend fun getTextResponse(prompt: PromptRequest): Result<TextAIResponse>
}