package com.matin.noora

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.matin.noora.navigation.NooraNavHost

@Composable
fun NooraApp(googleSignInClick: () -> Unit, appState: NooraAppState = rememberNooraAppState()) {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            NooraNavHost(googleSignInClick, appState)
        }
    }
}