package com.matin.noora.feature.auth

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SignInScreenRoute(onGoogleSignInClick: () -> Unit) {

    SignInScreen(onGoogleSignInClick)
}

@Composable
fun SignInScreen(onGoogleSignInClick: () -> Unit = {}) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = onGoogleSignInClick) {
            Text("Sign in with Google now")
        }
    }
}