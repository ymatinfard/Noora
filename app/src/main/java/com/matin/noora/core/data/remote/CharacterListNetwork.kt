package com.matin.noora.core.data.remote

import com.matin.noora.core.domain.model.ChatCharacterItem
import kotlin.time.ExperimentalTime

data class CharacterListNetwork(
    val characters: List<CharacterNetwork>
)

data class CharacterNetwork(
    val id: String,
    val name: String,
    val description: String,
) {
    @OptIn(ExperimentalTime::class)
    fun toDomain(): ChatCharacterItem {
        return ChatCharacterItem(
            id = id,
            name = name,
            description = description,
        )
    }
}

