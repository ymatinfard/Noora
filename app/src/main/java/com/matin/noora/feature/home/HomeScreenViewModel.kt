package com.matin.noora.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.noora.core.common.Result
import com.matin.noora.core.common.asResult
import com.matin.noora.core.domain.model.Tool
import com.matin.noora.core.domain.repository.AIRepository
import com.matin.noora.feature.chat.ChatCharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(val repository: AIRepository): ViewModel() {

    val characters = repository.getChatCharacters()
        .asResult()
        .map {
            when (it) {
                is Result.Success -> ChatCharactersState.Success(it.data)
                is Result.Error -> ChatCharactersState.Error(it.exception.message ?: "Unknown error")
                Result.Loading -> ChatCharactersState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = ChatCharactersState.Loading
        )

    val tools = repository.getTools()
        .asResult()
        .map {
            when (it) {
                is Result.Success -> ToolsState.Success(it.data)
                is Result.Error -> ToolsState.Error(it.exception.message ?: "Unknown error")
                Result.Loading -> ToolsState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = ToolsState.Loading
        )

    fun onToolClicked(tool: Tool){

    }
}

sealed interface ToolsState {
    data class Success(val tools: List<Tool>): ToolsState
    data class Error(val message: String): ToolsState
    object Loading: ToolsState
}