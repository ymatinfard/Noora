package com.matin.noora.feature.maindashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.feature.maindashboard.NooraMainDashboardRoute
import kotlinx.serialization.Serializable

@Serializable
data object MainDashboardRoute

fun NavGraphBuilder.mainDashboardRoute(onChatCharacterItemClicked: (ChatCharacterItem) -> Unit) {
    composable<MainDashboardRoute> {
        NooraMainDashboardRoute( onChatCharacterClicked = onChatCharacterItemClicked)
    }
}
