package com.matin.noora.core.domain.model

data class PromptRequest(
    val rawPrompt: String,
    val category: PromptCategory,
    val userId: String? = null,
    val sessionId: String? = null
)
