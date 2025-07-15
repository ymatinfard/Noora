package com.matin.noora.core.domain.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class ChatCharacterItem (
    val id: String = "A1",
    val name: String,
    val description: String,
    val timestamp: Instant? = null
)
