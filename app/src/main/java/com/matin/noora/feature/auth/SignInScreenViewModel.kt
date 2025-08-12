package com.matin.noora.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.noora.core.common.Result
import com.matin.noora.core.common.UiState
import com.matin.noora.core.common.asResult
import com.matin.noora.core.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val usernameState = MutableStateFlow("")
    val passwordState = MutableStateFlow("")
    val uiState = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val isSignButtonEnabled = combine(usernameState, passwordState) { username, password ->
        (username.isNotEmpty() && password.isNotEmpty())
    }

    fun signIn(username: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(username, password).asResult().collect { result ->
                val result = when (result) {
                    is Result.Success -> UiState.Success(Unit)
                    is Result.Error -> {
                        mapAuthError(result.exception)
                    }
                    is Result.Loading -> UiState.Loading
                }
                uiState.value = result
            }
        }
    }
}

fun mapAuthError(exception: Exception): UiState.Error {
    return when (exception) {
        is HttpException -> {
            when (exception.code()) {
                in 500 .. 599 -> UiState.Error(UiState.ErrorType.Server)
                in 400..499 -> UiState.Error(UiState.ErrorType.InvalidCredential)
                else -> UiState.Error(UiState.ErrorType.Unknown)
            }
        }
        else -> UiState.Error(UiState.ErrorType.Unknown)
    }
}

