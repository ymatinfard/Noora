package com.matin.noora.core.domain

import com.matin.noora.core.domain.model.PromptCategory
import com.matin.noora.core.domain.model.PromptRequest
import jakarta.inject.Inject

class PromptAnalyzerImpl @Inject constructor() : PromptAnalyzer {
    override fun createPromptRequest(
        rawPrompt: String,
        category: PromptCategory
    ): PromptRequest {
        // Validate the raw prompt
        return PromptRequest(
            rawPrompt = rawPrompt.trim(),
            category = category
        )
    }
}