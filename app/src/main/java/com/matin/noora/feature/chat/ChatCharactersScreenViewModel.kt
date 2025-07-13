package com.matin.noora.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.noora.core.common.Result
import com.matin.noora.core.common.asResult
import com.matin.noora.core.domain.SettingsRepository
import com.matin.noora.core.domain.model.UserScore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the Chat Characters screen..
 */
class ChatCharactersScreenViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    companion object {
        private const val WHILE_SUBSCRIBED_TIMEOUT_MS = 5_000L
    }

    val userScore = settingsRepository
        .getUserScore()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> UserScoreUiState.Success(result.data)
                is Result.Error -> UserScoreUiState.Error(result.exception.message ?: "Unknown error")
                Result.Loading -> UserScoreUiState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_TIMEOUT_MS),
            initialValue = UserScoreUiState.Loading
        )
}
sealed interface UserScoreUiState {
    data object Loading : UserScoreUiState
    data class Success(val userScore: UserScore) : UserScoreUiState
    data class Error(val message: String) : UserScoreUiState
}