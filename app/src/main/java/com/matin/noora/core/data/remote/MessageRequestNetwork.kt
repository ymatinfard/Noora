package com.matin.noora.core.data.remote

data class MessageRequestNetwork(
    val prompt: String,
    val categoryId: String,
    val userId: String? = null,
    val sessionId: String? = null
)
