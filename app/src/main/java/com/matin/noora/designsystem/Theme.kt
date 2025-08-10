package com.matin.noora.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = darkColorScheme(
    primary = CornflowerBlue,
    onPrimary = OnCornflowerBlue,
    primaryContainer = LightCornflowerBlue,
    onPrimaryContainer = CornflowerBlue,
    secondary = CornflowerBlue,
    onSecondary = OnCornflowerBlue,
    background = SurfaceBackground,
    onBackground = TextPrimary,
    surface = SurfaceBackground,
    onSurface = TextPrimary,
    outline = TextSecondary,
    outlineVariant = CornflowerDeepBlueLight,
    surfaceContainer = CardBackground,
    surfaceContainerHighest = CardBackground,
)

private val DarkColorScheme = lightColorScheme(
    primary = CornflowerBlueDark,
    onPrimary = OnCornflowerBlueDark,
    primaryContainer = LightCornflowerBlueDark,
    onPrimaryContainer = CornflowerBlueDark,
    secondary = CornflowerBlueDark,
    onSecondary = OnCornflowerBlueDark,
    background = SurfaceBackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceBackgroundDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = CardBackgroundDark,
    outline = TextSecondaryDark,
    outlineVariant = CornflowerDeepBlueDark,
    surfaceContainer = CardBackgroundDark,
    surfaceContainerHighest = CardBackgroundDark,
)

@Composable
fun NooraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}