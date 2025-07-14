package com.matin.noora.core.domain.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class ChatRecentHistoryItem (
    val chatCharacter: ChatCharacter,
    val lastMessage: String,
    val timestamp: Instant? = null
)
