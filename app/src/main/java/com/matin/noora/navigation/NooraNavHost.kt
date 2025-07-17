package com.matin.noora.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.matin.noora.feature.chat.navigation.chatDashboardScreen
import com.matin.noora.feature.home.navigation.HomeRoute
import com.matin.noora.feature.home.navigation.homeScreen

@Composable
fun NooraNavHost(navController: NavHostController) {
    NavHost(
        startDestination = HomeRoute,
        navController = navController
    ) {
        homeScreen()
        chatDashboardScreen()
    }
}