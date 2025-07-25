package com.matin.noora.feature.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.noora.designsystem.component.ChatTopBar
import com.matin.noora.designsystem.component.MessageInputBar
import com.matin.noora.designsystem.component.MessageList
import com.matin.noora.designsystem.component.PermissionRequestHandler

@Composable
fun ChatScreenRoute(
    modifier: Modifier = Modifier,
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
        onSearchClick = onSearchClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    state: ChatUiState,
    onIntent: (ChatIntent) -> Unit,
    onInfoClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNavigateBack: () -> Unit,
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val shouldShowSendButton by remember(state.currentMessage) {
        derivedStateOf { state.currentMessage.isNotBlank() }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ChatTopBar(
                userName = state.userName,
                scrollBehavior = scrollBehavior,
                onBackClick = onNavigateBack,
                onInfoClick = onInfoClick,
                onSearchClick = onSearchClick
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            MessageList(
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
                messages = state.messages,
                listState = listState,
                isMsgPending = state.isMsgPending,
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
