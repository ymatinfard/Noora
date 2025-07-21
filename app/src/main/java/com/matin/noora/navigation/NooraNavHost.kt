package com.matin.noora.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.matin.noora.feature.chat.navigation.chatDashboardScreen
import com.matin.noora.feature.chat.navigation.chatScreen
import com.matin.noora.feature.chat.navigation.navigateToChat
import com.matin.noora.feature.home.navigation.HomeRoute
import com.matin.noora.feature.home.navigation.homeScreen

@Composable
fun NooraNavHost(navController: NavHostController) {
    NavHost(
        startDestination = HomeRoute,
        navController = navController,
        enterTransition = {
            fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(400))
        }
    ) {
        homeScreen()
        chatDashboardScreen(onChatCharacterClicked = {
            navController.navigateToChat(it.id)
        })
        chatScreen()
    }
}