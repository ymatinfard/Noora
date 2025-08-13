package com.matin.noora.core.domain.repository

import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.core.domain.model.Message
import com.matin.noora.core.domain.model.Tool
import kotlinx.coroutines.flow.Flow

interface AIRepository {
    fun sendMessage(message: Message)
    fun getChatCharacters(): Flow<List<ChatCharacterItem>>
    fun getTools(): Flow<List<Tool>>
    fun getChatRecentHistory(): Flow<List<ChatCharacterItem>>
    fun getChatMessages(categoryId: String): Flow<List<Message>>
    fun getLastMessageSnapshot(categoryId: String): List<Message>?
    fun chatWarmUp(categoryId: String)
    fun isMessagePending(): Flow<Boolean>
}