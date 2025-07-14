package com.matin.noora.core.data.remote

import com.matin.noora.core.domain.model.Character

data class CharacterListNetwork(
    val characters: List<CharacterNetwork>
)

data class CharacterNetwork(
    val id: String,
    val name: String,
    val description: String,
) {
    fun toDomain(): Character {
        return Character(
            id = id,
            name = name,
            description = description
        )
    }
}

