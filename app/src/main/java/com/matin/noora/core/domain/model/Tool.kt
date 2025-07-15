package com.matin.noora.core.domain.model

data class Tool(
    val id: String = "T1",
    val name: String,
    val description: String,
    val iconUrl: String? = null,
    val imgRes: Int,
    val isEnabled: Boolean = true
)
