package com.matin.noora.feature.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.feature.chat.ChatDashboardScreenRoute
import com.matin.noora.feature.chat.ChatScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object ChatDashboardRoute
@Serializable
data class ChatRoute(val categoryId: String, val name: String)

fun NavController.navigateToChatDashboard(navOptions: NavOptions? = null) = this.navigate(ChatDashboardRoute, navOptions)
fun NavController.navigateToChat(categoryId: String, name: String) = this.navigate(ChatRoute(categoryId, name))

fun NavGraphBuilder.chatDashboardScreen(onChatCharacterClicked: (ChatCharacterItem) -> Unit) {
    composable<ChatDashboardRoute> {
        ChatDashboardScreenRoute(onChatCharacterClicked = onChatCharacterClicked)
    }
}

fun NavGraphBuilder.chatScreen(
    onNavigateBack: () -> Unit,
    onSearchClick: () -> Unit = {},
    onInfoClick: () -> Unit = {},
) {
    composable<ChatRoute> {
        ChatScreenRoute(
            onNavigateBack = onNavigateBack,
            onSearchClick = onSearchClick,
            onInfoClick = onInfoClick
        )
    }
}