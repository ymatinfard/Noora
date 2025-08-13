package com.matin.noora.feature.chat

import android.Manifest
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.noora.core.domain.model.Message
import com.matin.noora.core.domain.repository.AIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val aiRepository: AIRepository,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    val categoryId = saveStateHandle.get<String>("categoryId") ?: "noora"
    val userName = saveStateHandle.get<String>("name") ?: "Noora"

    init {
        loadUsername(userName)
        loadMessages(categoryId)
        checkMessageStatus()
    }

    private fun loadUsername(userName: String) {
        _uiState.update { it.copy(userName = userName.capitalizeFirstLetter()) }
    }

    private fun loadMessages(categoryId: String) {
        viewModelScope.launch {
            aiRepository.getChatMessages(categoryId)
                .catch { e ->
                    _uiState.update { currentState ->
                        currentState.copy(messages = emptyList())
                    }
                }
                .collectLatest { messages ->
                    _uiState.update { currentState ->
                        currentState.copy(messages = messages)
                    }
                }
        }
    }

    fun onUpdateMessage(text: String) {
        _uiState.update { it.copy(currentMessage = text) }
    }

    private fun onSendMessage() {
        val currentText = _uiState.value.currentMessage.trim()
        if (currentText.isNotBlank()) {
                aiRepository.sendMessage(Message(text = currentText, categoryId = categoryId))
                // Clear input field after sending
                _uiState.update {
                    it.copy(
                        currentMessage = "",
                    )
                }
        }
    }

    private fun checkMessageStatus() {
        viewModelScope.launch {
            aiRepository.isMessagePending().collectLatest { isPending ->
                _uiState.update {
                    it.copy(isMsgPending = isPending)
                }
            }
        }
    }

    fun requestPermission(permission: String) {
        _uiState.update {
            it.copy(
                pendingPermissions = it.pendingPermissions + permission
            )
        }
    }

    fun onPermissionResult(permissions: Map<String, Boolean>) {
        val newPermissions = _uiState.value.pendingPermissions - permissions.keys

        _uiState.update {
            it.copy(pendingPermissions = newPermissions)
        }

        permissions.forEach { (permission, isGranted) ->
            if (!isGranted) return@forEach
            when (permission) {
                Manifest.permission.READ_MEDIA_IMAGES -> {
                    _uiState
                        .update { it.copy(isShowingPhotoPicker = true) }
                }

                Manifest.permission.RECORD_AUDIO -> {
                    _uiState.update { it.copy(isRecording = true) }
                }
            }
        }
    }

    fun onMessageClick(messageId: String) {
    }

    fun onIntent(event: ChatIntent) {
        when (event) {
            is ChatIntent.UpdateMessage -> onUpdateMessage(event.text)
            is ChatIntent.SendMessage -> onSendMessage()
            is ChatIntent.RequestPermission -> requestPermission(event.permission)
            is ChatIntent.PermissionResult -> onPermissionResult(event.permissions)
            is ChatIntent.MessageClick -> onMessageClick(event.messageId)
        }
    }
}

data class ChatUiState(
    val userName: String = "",
    val messages: List<Message> = emptyList(),
    val isMsgPending: Boolean = false,
    val currentMessage: String = "",
    val isRecording: Boolean = false,
    val isShowingPhotoPicker: Boolean = false,
    val pendingPermissions: Set<String> = emptySet()
)

sealed class ChatIntent {
    data class UpdateMessage(val text: String) : ChatIntent()
    object SendMessage : ChatIntent()
    data class RequestPermission(val permission: String) : ChatIntent()
    data class PermissionResult(val permissions: Map<String, Boolean>) : ChatIntent()
    data class MessageClick(val messageId: String) : ChatIntent()
}
