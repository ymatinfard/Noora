package com.matin.noora.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.matin.noora.NooraAppState
import com.matin.noora.feature.auth.navigation.signInScreen
import com.matin.noora.feature.chat.navigation.chatScreen
import com.matin.noora.feature.maindashboard.navigation.MainDashboardRoute
import com.matin.noora.feature.maindashboard.navigation.mainDashboardRoute

@Composable
fun NooraNavHost(googleSignInClick: () -> Unit, appState: NooraAppState) {
    NavHost(
        startDestination = MainDashboardRoute,
        navController = appState.navController,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
    ) {
        mainDashboardRoute(
            onChatCharacterItemClicked = { category ->
                appState.navigateToChat(category.id, category.name)
            }
        )
        chatScreen(
            onNavigateBack = { appState.navigateBack() },
            onSearchClick = { },
            onInfoClick = {},
        )

        signInScreen(googleSignInClick)
    }
}