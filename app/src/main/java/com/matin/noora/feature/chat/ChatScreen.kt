package com.matin.noora.feature.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.noora.designsystem.component.ChatTopBar
import com.matin.noora.designsystem.component.MessageInputBar
import com.matin.noora.designsystem.component.MessageList
import com.matin.noora.designsystem.component.PermissionRequestHandler

@Composable
fun ChatScreenRoute(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onInfoClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    ChatScreen(
        state = state.value,
        onIntent = viewModel::onIntent,
        onNavigateBack = onNavigateBack,
        onInfoClick = onInfoClick,
        onSearchCloseClick = onSearchClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatUiState,
    onIntent: (ChatIntent) -> Unit,
    onInfoClick: () -> Unit = {},
    onSearchCloseClick: () -> Unit = {},
    onNavigateBack: () -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty())
            listState.scrollToItem(0)
    }

    val shouldShowSendButton by remember(state.currentMessage) {
        derivedStateOf { state.currentMessage.isNotBlank() }
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize().consumeWindowInsets(WindowInsets.systemBars)
    ) {
        ChatTopBar(
            userName = state.userName,
            onBackClick = onNavigateBack,
            onSearchCloseClick = {
                searchQuery = ""
            },
            query = searchQuery,
            onQueryChange = { newQuery ->
                searchQuery = newQuery
            })
        Column(modifier = Modifier.weight(1f)) {
            MessageList(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                messages = state.messages,
                listState = listState,
                isMsgPending = state.isMsgPending,
                query = searchQuery,
            )

            MessageInputBar(
                message = state.currentMessage,
                isSendButtonEnabled = shouldShowSendButton && !state.isMsgPending,
                onMessageChange = { onIntent(ChatIntent.UpdateMessage(it)) },
                onSendClick = { onIntent(ChatIntent.SendMessage) },
                modifier = Modifier.imePadding()
            )
        }
    }

    PermissionRequestHandler(
        permissionsToRequest = state.pendingPermissions,
        onPermissionResult = { onIntent(ChatIntent.PermissionResult(it)) },
    )
}