package com.matin.noora.core.domain

import com.matin.noora.core.domain.model.PromptCategory
import com.matin.noora.core.domain.model.PromptRequest

interface PromptAnalyzer {
    fun createPromptRequest(rawPrompt: String, category: PromptCategory): PromptRequest
}