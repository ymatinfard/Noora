package com.matin.noora.core.data.local

import com.matin.noora.core.domain.model.ChatCharacter
import com.matin.noora.core.domain.repository.ChatLocalRepository
import com.matin.noora.core.domain.model.ChatRecentHistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ChatLocalRepositoryImpl @Inject constructor() : ChatLocalRepository {
    @OptIn(ExperimentalTime::class)
    override fun getChatRecentHistory(): Flow<List<ChatRecentHistoryItem>> {
        return flowOf(
            listOf(
                ChatRecentHistoryItem(
                    chatCharacter = ChatCharacter.ALI,
                    lastMessage = "Hello, how can I assist you today?",
                    timestamp = Instant.fromEpochMilliseconds(167300000000L), // Example timestamp
                ),
                ChatRecentHistoryItem(
                    chatCharacter = ChatCharacter.NOORA,
                    lastMessage = "What would you like to know?",
                    timestamp = Instant.fromEpochMilliseconds(167300100000L), // Example timestamp
                ),
                ChatRecentHistoryItem(
                    chatCharacter = ChatCharacter.MATIN,
                    lastMessage = "I can help you with that.",
                    timestamp = Instant.fromEpochMilliseconds(167300200000L), // Example timestamp
                )
            )
        )
    }
}