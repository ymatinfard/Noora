package com.matin.noora.core.data.remote

import com.matin.noora.core.domain.model.TextAIResponse

data class TextAINetwork(
    val text: String,
    val temperature: Double = 0.0,
) {
    fun toDomain(): TextAIResponse {
        return TextAIResponse(text = text)
    }
}
