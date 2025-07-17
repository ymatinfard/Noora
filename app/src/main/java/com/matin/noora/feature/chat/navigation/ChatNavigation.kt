package com.matin.noora.feature.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.noora.feature.chat.ChatDashboardScreenRoute
import kotlinx.serialization.Serializable

@Serializable data object ChatDashboardRoute

fun NavController.navigateToChatDashboard() = this.navigate(ChatDashboardRoute)

fun NavGraphBuilder.chatDashboardScreen() {
    composable<ChatDashboardRoute> {
        ChatDashboardScreenRoute()
    }
}