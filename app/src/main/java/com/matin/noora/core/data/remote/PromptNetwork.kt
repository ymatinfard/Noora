package com.matin.noora.core.data.remote

data class PromptNetwork(
    val prompt: String,
    val category: String,
    val userId: String? = null,
    val sessionId: String? = null
)
