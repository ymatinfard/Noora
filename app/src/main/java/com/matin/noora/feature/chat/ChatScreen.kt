package com.matin.noora.feature.chat

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ChatScreenRoute(modifier: Modifier = Modifier, viewModel: ChatViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ChatScreen(state = uiState.value, onEvent = viewModel::onEvent)
}

@Composable
fun ChatScreen(
    state: ChatUiState,
    onEvent: (ChatEvent) -> Unit,
) {

}