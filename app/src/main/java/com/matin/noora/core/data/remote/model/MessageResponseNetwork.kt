package com.matin.noora.core.data.remote.model

import com.matin.noora.core.domain.model.Message
import com.matin.noora.core.domain.model.MessageAuthor
import com.matin.noora.core.domain.model.MessageState

data class MessageResponseNetwork(
    val text: String,
    val categoryId: String,
    val temperature: Double = 0.0,
)

fun MessageResponseNetwork.toDomain() = Message(
    text = text,
    author = MessageAuthor.Server,
    state = MessageState.DONE,)
