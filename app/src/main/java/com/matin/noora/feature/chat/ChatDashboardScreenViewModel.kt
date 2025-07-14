package com.matin.noora.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.noora.core.common.Result
import com.matin.noora.core.common.asResult
import com.matin.noora.core.domain.model.ChatItemSummary
import com.matin.noora.core.domain.model.UserScore
import com.matin.noora.core.domain.repository.AIRepository
import com.matin.noora.core.domain.repository.ChatLocalRepository
import com.matin.noora.core.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


@HiltViewModel
class ChatDashboardScreenViewModel @Inject constructor(
    private val chatLocalRepository: ChatLocalRepository,
    private val aiRepository: AIRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val userScore = settingsRepository
        .getUserScore()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> UserScoreUiState.Success(result.data)
                is Result.Error -> UserScoreUiState.Error(
                    result.exception.message ?: "Unknown error"
                )

                Result.Loading -> UserScoreUiState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT_MS),
            initialValue = UserScoreUiState.Loading
        )

    val recentChatHistory = chatLocalRepository
        .getChatRecentHistory()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> ChatCharactersState.Success(result.data)
                is Result.Error -> ChatCharactersState.Error(
                    result.exception.message ?: "Unknown error"
                )

                Result.Loading -> ChatCharactersState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT_MS),
            initialValue = ChatCharactersState.Loading
        )

    val chatCharacters = aiRepository
        .getChatCharacters()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> ChatCharactersState.Success(result.data.map { it.toSummary() })
                is Result.Error -> ChatCharactersState.Error(
                    result.exception.message ?: "Unknown error"
                )

                Result.Loading -> ChatCharactersState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT_MS),
            initialValue = ChatCharactersState.Loading
        )

    fun onChatRecentHistoryItemClicked(chatHistoryItem: ChatItemSummary) {
        // Handle click on chat history item
        // This could navigate to a chat screen with the selected character
    }

    fun onChatCharacterItemClicked(chatCharacterItem: ChatItemSummary) {
        // Handle click on chat character item
        // This could navigate to a chat screen with the selected character
    }

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT_MS = 5_000L
    }
}

sealed interface UserScoreUiState {
    data object Loading : UserScoreUiState
    data class Success(val userScore: UserScore) : UserScoreUiState
    data class Error(val message: String) : UserScoreUiState
}

sealed interface ChatCharactersState {
    data object Loading : ChatCharactersState
    data class Success(val characters: List<ChatItemSummary>) : ChatCharactersState
    data class Error(val message: String) : ChatCharactersState
}