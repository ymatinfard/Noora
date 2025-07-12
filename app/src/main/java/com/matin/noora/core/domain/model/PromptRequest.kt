package com.matin.noora.core.domain.model

data class PromptRequest(
    val rawPrompt: String,
    val category: PromptCategory,
)
