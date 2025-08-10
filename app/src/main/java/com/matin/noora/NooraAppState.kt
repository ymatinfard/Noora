package com.matin.noora

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.matin.noora.core.common.TopLevelDestination
import com.matin.noora.feature.chat.navigation.navigateToChat
import com.matin.noora.feature.chat.navigation.navigateToChatDashboard
import com.matin.noora.feature.home.navigation.navigateToHome

class NooraAppState(
    val navController: NavHostController,
) {

    val prevDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry =
                navController.currentBackStackEntryFlow.collectAsState(initial = null)

            return currentEntry.value?.destination?.also { destination ->
                prevDestination.value = destination
            } ?: prevDestination.value
        }

    fun navigationToTopLevelDestination(destination: TopLevelDestination) {

        val navOptions = navOptions {
            popUpTo(
                navController.graph.findStartDestination().id
            ) { saveState = true }

            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            TopLevelDestination.Home -> navController.navigateToHome(navOptions)
            TopLevelDestination.ChatDashboard -> navController.navigateToChatDashboard(navOptions)
            else -> navController.navigateToHome(navOptions)
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToChat(categoryId: String, categoryName: String) {
        navController.navigateToChat(categoryId, categoryName)
    }
}

@Composable
fun rememberNooraAppState(navController: NavHostController = rememberNavController()): NooraAppState {
    val state = remember { NooraAppState(navController) }

    return state
}