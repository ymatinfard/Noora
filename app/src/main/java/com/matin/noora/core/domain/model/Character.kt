package com.matin.noora.core.domain.model

import kotlin.time.ExperimentalTime

data class Character(
    val id: String,
    val name: String,
    val description: String? = null,
) {
    @OptIn(ExperimentalTime::class)
    fun toSummary(): ChatItemSummary {
        return ChatItemSummary(
            id = id,
            name = name,
            lastMessage = description ?: "",
        )
    }
}
