package com.matin.noora.core.domain.model

import com.matin.noora.core.common.ChatIdGenerator
import com.matin.noora.core.data.local.model.MessageEntity
import com.matin.noora.core.data.remote.MessageRequestNetwork
import com.matin.noora.core.data.remote.TextAINetwork
import java.time.Instant

const val DEFAULT_USER_ID = "noora"

abstract class BaseMessage(
    open val id: String = ChatIdGenerator.nextId(),
    open val prompt: Prompt,
    open val response: String = "",
    open val categoryId: String,
    open val createdAt: Long = Instant.now().toEpochMilli(),
    open val state: MessageState,
) {
    abstract val type: MessageType
}

data class Message(
    override val id: String = ChatIdGenerator.nextId(),
    override val prompt: Prompt,
    override val response: String,
    override val categoryId: String = DEFAULT_USER_ID,
    override val createdAt: Long = Instant.now().toEpochMilli(),
    override var state: MessageState = MessageState.PENDING,
) : BaseMessage(id, prompt, response, categoryId, createdAt, state) {
    override val type: MessageType = MessageType.TEXT
}

/**
 * Factory methods to create messages
 */
object MessageFactory {
    fun createMessage(
        prompt: Prompt,
        categoryId: String,
    ): Message {
        return Message(
            prompt = prompt,
            response = "",
            categoryId = categoryId,
        )
    }
}

fun Message.toNetwork(): MessageRequestNetwork {
    return MessageRequestNetwork(
        prompt = prompt.value,
        categoryId = categoryId,
    )
}

fun Message.toEntity(state: MessageState): MessageEntity {
    return MessageEntity(
        id = ChatIdGenerator.nextId(),
        prompt = prompt.value,
        response = response,
        categoryId = categoryId,
        type = MessageType.TEXT,
        timestamp = Instant.now().toEpochMilli(),
        state = state,
    )
}

fun MessageEntity.toDomain(): Message {
    return Message(
        id = id,
        prompt = Prompt(value = response),
        categoryId = categoryId,
        response = response,
        createdAt = timestamp,
        state = state,
    )
}