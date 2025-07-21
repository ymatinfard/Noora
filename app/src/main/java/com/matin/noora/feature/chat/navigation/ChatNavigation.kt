package com.matin.noora.feature.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.feature.chat.ChatDashboardScreenRoute
import com.matin.noora.feature.chat.ChatScreenRoute
import kotlinx.serialization.Serializable

@Serializable data object ChatDashboardRoute
@Serializable data class ChatRoute(val categoryId: String)

fun NavController.navigateToChatDashboard() = this.navigate(ChatDashboardRoute)
fun NavController.navigateToChat(categoryId: String) = this.navigate(ChatRoute(categoryId))

fun NavGraphBuilder.chatDashboardScreen(onChatCharacterClicked: (ChatCharacterItem) -> Unit) {
    composable<ChatDashboardRoute> {
        ChatDashboardScreenRoute(onChatCharacterClicked = onChatCharacterClicked )
    }
}

fun NavGraphBuilder.chatScreen() {
    composable<ChatRoute> {
        ChatScreenRoute()
    }
}