package com.matin.noora.core.domain.model

import com.matin.noora.core.common.ChatIdGenerator
import com.matin.noora.core.data.local.model.MessageEntity
import com.matin.noora.core.data.remote.model.MessageRequestNetwork
import java.time.Instant
import kotlin.concurrent.timer

const val DEFAULT_USER_ID = "noora"


data class Message(
    val id: String = ChatIdGenerator.nextId(),
    val text: String,
    val author: MessageAuthor = MessageAuthor.Me,
    val categoryId: String = DEFAULT_USER_ID,
    val createdAt: Long = Instant.now().toEpochMilli(),
    var state: MessageState = MessageState.PENDING,
)

fun Message.toNetwork(): MessageRequestNetwork {
    return MessageRequestNetwork(
        text = text,
        categoryId = categoryId,
    )
}

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = id,
        text = text,
        categoryId = categoryId,
        type = MessageType.TEXT,
        timestamp = createdAt,
        author = author,
        state = state,
    )
}

fun MessageEntity.toDomain(): Message {
    return Message(
        id = id,
        text = text,
        author = author,
        categoryId = categoryId,
        createdAt = timestamp,
        state = state,
    )
}

enum class MessageAuthor {
    Me,
    Server,
}