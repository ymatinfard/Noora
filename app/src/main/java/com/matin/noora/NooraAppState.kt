package com.matin.noora

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.matin.noora.core.common.TopLevelDestination
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

    fun navigationTo(destination: TopLevelDestination) {
        when (destination) {
            TopLevelDestination.Home -> navController.navigateToHome()
            TopLevelDestination.ChatDashboard -> navController.navigateToChatDashboard()
            else -> navController.navigateToHome()
        }
    }
}

@Composable
fun rememberNooraAppState(navController: NavHostController = rememberNavController()): NooraAppState {
    val state = remember { NooraAppState(navController) }

    return state
}