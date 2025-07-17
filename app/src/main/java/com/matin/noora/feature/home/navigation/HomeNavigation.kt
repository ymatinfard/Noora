package com.matin.noora.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.noora.feature.home.HomeScreenRoute
import kotlinx.serialization.Serializable

@Serializable data object HomeRoute

fun NavController.navigateToHome() = this.navigate(HomeRoute)

fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        HomeScreenRoute()
    }
}