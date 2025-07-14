package com.matin.noora.core.domain.repository

import com.matin.noora.core.domain.model.ChatItemSummary
import kotlinx.coroutines.flow.Flow

interface ChatLocalRepository {
    fun getChatRecentHistory(): Flow<List<ChatItemSummary>>
}