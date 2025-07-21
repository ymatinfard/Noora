package com.matin.noora.core.data.remote

data class TextAINetwork(
    val text: String,
    val categoryId: String,
    val temperature: Double = 0.0,
)
