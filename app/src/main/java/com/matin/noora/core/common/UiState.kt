package com.matin.noora.core.common

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data object Initial : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Error(val type: ErrorType) : UiState<Nothing>

    enum class ErrorType {
        InvalidCredential,
        Server,
        Unknown
    }
}