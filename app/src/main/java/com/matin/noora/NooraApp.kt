package com.matin.noora

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.matin.noora.designsystem.component.NooraNavigationSuitScaffold
import com.matin.noora.navigation.NooraNavHost

@Composable
fun NooraApp(modifier: Modifier = Modifier, appState: NooraAppState = rememberNooraAppState()) {
    NooraNavigationSuitScaffold(appState = appState) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                NooraNavHost(navController = appState.navController)
            }
        }
    }
}