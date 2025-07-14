package com.matin.noora.feature.chat

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.noora.core.domain.model.ChatItemSummary
import com.matin.noora.core.domain.model.UserScore
import com.matin.noora.designsystem.NooraTheme
import com.matin.noora.designsystem.component.ChatCharacterRow
import com.matin.noora.designsystem.component.NooraTopAppBar
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun ChatDashboardScreenRoute(viewModel: ChatDashboardScreenViewModel = hiltViewModel()) {
    val userScoreState by viewModel.userScore.collectAsStateWithLifecycle()
    val chatRecentHistoryState by viewModel.recentChatHistory.collectAsStateWithLifecycle()
    val chatCharactersState by viewModel.chatCharacters.collectAsStateWithLifecycle()

    ChatDashboardScreen(
        userScore = userScoreState,
        chatRecentHistoryState = chatRecentHistoryState,
        chatCharactersState = chatCharactersState,
        onChatRecentHistoryItemClicked = viewModel::onChatRecentHistoryItemClicked,
        onChatCharacterItemClicked = viewModel::onChatCharacterItemClicked
    )
}

@Composable
fun ChatDashboardScreen(
    userScore: UserScoreUiState,
    chatRecentHistoryState: ChatCharactersState,
    chatCharactersState: ChatCharactersState,
    onChatRecentHistoryItemClicked: (ChatItemSummary) -> Unit = {},
    onChatCharacterItemClicked: (ChatItemSummary) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        NooraTopAppBar(title = "Chat", userScore = userScore)
        Spacer(Modifier.height(32.dp))
        ChatCard(
            title = "Recent Chats",
            state = chatRecentHistoryState,
            onClick = onChatRecentHistoryItemClicked
        )
        Spacer(Modifier.height(16.dp))
        ChatCard(
            title = "Chat Characters",
            state = chatCharactersState,
            onClick = onChatCharacterItemClicked
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun ChatCard(
    title: String,
    state: ChatCharactersState,
    onClick: (ChatItemSummary) -> Unit
) {
    when (state) {
        is ChatCharactersState.Loading -> {
            Text(text = "Loading...")
        }

        is ChatCharactersState.Error -> {
            Text(text = "Error loading chat: ${state.message}")
        }

        is ChatCharactersState.Success -> {
            ChatCardContent(title = title, state.characters, onClick)
        }
    }
}

@Composable
@OptIn(ExperimentalTime::class)
private fun ChatCardContent(
    title: String,
    chatItems: List<ChatItemSummary>,
    onClick: (ChatItemSummary) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            items(items = chatItems) { item ->
                ChatCharacterRow(
                    name = item.name,
                    message = item.lastMessage,
                    date = item.timestamp,
                    onClick = {
                        onClick(item)
                    }
                )

                if (item != chatItems.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}

fun String.capitalizeFirstLetter(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}

@OptIn(ExperimentalTime::class)
@Preview(
    showBackground = true,
    name = "ChatDashboardScreenPreview",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ChatDashboardScreenPreview() {
    NooraTheme {
        ChatDashboardScreen(
            userScore = UserScoreUiState.Success(
                userScore = UserScore(
                    streak = 10,
                    badge = 5,
                    like = 129,
                )
            ),
            chatRecentHistoryState = ChatCharactersState.Success(
                characters = listOf(
                    ChatItemSummary(
                        name = "ali",
                        lastMessage = "Hello, how are you?",
                        timestamp = Instant.fromEpochMilliseconds(1633072800000L)
                    ),
                    ChatItemSummary(
                        name = "shahin",
                        lastMessage = "I'm fine, thanks!",
                        timestamp = Instant.fromEpochMilliseconds(1633076400000L)
                    )
                )
            ),
            chatCharactersState = ChatCharactersState.Success(
                characters = listOf(
                    ChatItemSummary(
                        name = "noora",
                        lastMessage = "Hello, how can I assist you today?",
                    ),
                    ChatItemSummary(
                        name = "matin",
                        lastMessage = "What would you like to know?",
                    )
                )
            ),
        )
    }
}