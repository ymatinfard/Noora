package com.matin.noora.core.data.remote.model

data class MessageRequestNetwork(
    val text: String,
    val categoryId: String,
    val userId: String? = null,
    val sessionId: String? = null
)
