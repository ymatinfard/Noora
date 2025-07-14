package com.matin.noora.core.domain.usecase

import com.matin.noora.core.common.Result
import com.matin.noora.core.domain.PromptAnalyzer
import com.matin.noora.core.domain.repository.AIRepository
import com.matin.noora.core.domain.model.TextAIResponse
import com.matin.noora.core.domain.model.Prompt
import com.matin.noora.core.domain.model.PromptCategory
import javax.inject.Inject

class GenerateTextUseCase @Inject constructor(
    private val aiRepository: AIRepository,
    private val promptAnalyzer: PromptAnalyzer
) {
    suspend operator fun invoke(prompt: Prompt, category: PromptCategory): Result<TextAIResponse> {
        val promptRequest = promptAnalyzer.createPromptRequest(prompt.value, category)
        return aiRepository.getTextResponse(promptRequest)
    }
}