package com.matin.noora.core.domain.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class ChatItemSummary (
    val id: String = "A1",
    val name: String,
    val lastMessage: String,
    val timestamp: Instant? = null
)
