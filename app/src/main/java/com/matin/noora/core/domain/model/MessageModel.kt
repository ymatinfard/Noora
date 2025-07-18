package com.matin.noora.core.domain.model

import android.net.Uri
import com.matin.noora.core.common.ChatIdGenerator
import java.time.Instant

const val CURRENT_USER_ID = "noora"

abstract class BaseMessage(
    open val id: String = ChatIdGenerator.nextId(),
    open val content: String = "",
    open val author: String,
    open val createdAt: Long = Instant.now().toEpochMilli(),
    open val state: MessageState,
) {
    abstract val type: MessageType
}

data class Message(
    override val id: String = ChatIdGenerator.nextId(),
    override val content: String,
    override val author: String = CURRENT_USER_ID,
    override val createdAt: Long = Instant.now().toEpochMilli(),
    override val state: MessageState = MessageState.PENDING,
) : BaseMessage(id, content, author, createdAt, state) {
    override val type: MessageType = MessageType.TEXT
}

data class ImageMessage(
    override val id: String = ChatIdGenerator.nextId(),
    override val content: String = "", // Optional caption
    override val author: String,
    override val createdAt: Long = Instant.now().toEpochMilli(),
    override val state: MessageState = MessageState.PENDING,
    val imageUri: String,
    val width: Int? = null,
    val height: Int? = null,
) : BaseMessage(id, content, author, createdAt, state) {
    override val type: MessageType = MessageType.IMAGE
}

data class VoiceMessage(
    override val id: String = ChatIdGenerator.nextId(),
    override val content: String = "", // Optional transcription
    override val author: String,
    override val createdAt: Long = Instant.now().toEpochMilli(),
    override val state: MessageState = MessageState.PENDING,
    val voicePath: Uri,
    val durationMs: Long,
) : BaseMessage(id, content, author, createdAt, state) {
    override val type: MessageType = MessageType.VOICE
}


/**
 * Factory methods to create messages
 */
object MessageFactory {
    fun createMessage(
        content: String,
        author: String = CURRENT_USER_ID
    ): Message {
        return Message(
            content = content,
            author = author,
        )
    }
}

fun createImageMessage(
    imageUri: String,
    caption: String = "",
    author: String = CURRENT_USER_ID
): ImageMessage {
    return ImageMessage(
        content = caption,
        imageUri = imageUri,
        author = author
    )
}

fun createVoiceMessage(
    voicePath: Uri,
    durationMs: Long,
    transcription: String = "",
    author: String = CURRENT_USER_ID
): VoiceMessage {
    return VoiceMessage(
        content = transcription,
        voicePath = voicePath,
        durationMs = durationMs,
        author = author
    )
}