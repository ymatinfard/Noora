package com.matin.noora.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.noora.feature.auth.SignInScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object SignInRoute

fun NavController.navigateToSignIn() = this.navigate(SignInRoute)

fun NavGraphBuilder.signInScreen(onGoogleSignInClick: () -> Unit) {
    composable<SignInRoute> {
        SignInScreenRoute(onGoogleSignInClick)
    }
}