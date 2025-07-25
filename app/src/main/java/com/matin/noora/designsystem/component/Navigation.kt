package com.matin.noora.designsystem.component

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.matin.noora.NooraAppState
import com.matin.noora.core.common.TopLevelDestination
import com.matin.noora.designsystem.NooraTheme
import com.matin.noora.rememberNooraAppState
import kotlin.reflect.KClass

@Composable
fun NooraNavigationSuitScaffold(
    modifier: Modifier = Modifier,
    appState: NooraAppState = rememberNooraAppState(),
    content: @Composable () -> Unit
) {
    val currentDestination = appState.currentDestination

    val showBottomNavigation =
        TopLevelDestination.entries.any {
            currentDestination.isRouteInHierarchy(it.route)
        }

    val navigationSuitColor = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = NooraNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NooraNavigationDefaults.navigationContentColor(),
            selectedTextColor = NooraNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NooraNavigationDefaults.navigationContentColor(),
            indicatorColor = NooraNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = NooraNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NooraNavigationDefaults.navigationContentColor(),
            selectedTextColor = NooraNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NooraNavigationDefaults.navigationContentColor(),
            indicatorColor = NooraNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = NooraNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NooraNavigationDefaults.navigationContentColor(),
            selectedTextColor = NooraNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NooraNavigationDefaults.navigationContentColor(),
        )
    )

    NavigationSuiteScaffold(
        modifier = modifier,
        layoutType = if (showBottomNavigation) NavigationSuiteType.NavigationBar else NavigationSuiteType.None,
        navigationSuiteItems = {

            TopLevelDestination.entries.forEach { destination ->
                val selected = currentDestination.isRouteInHierarchy(destination.route)
                item(
                    onClick = {
                        appState.navigationTo(destination)
                    },
                    selected = selected,
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            val animatedValue =
                                animateDpAsState(
                                    targetValue = if (selected) 80.dp else 0.dp,
                                    animationSpec = tween(durationMillis = 100)
                                )

                            Box(
                                Modifier
                                    .width(animatedValue.value)
                                    .height(3.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(1.5.dp)
                                    )
                            )

                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.title
                            )
                        }
                    },
                    colors = navigationSuitColor,
                    label = { Text(text = destination.title) }
                )
            }
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = Color.Transparent,
        ),
        content = content
    )
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean {
    return this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
}

object NooraNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews

@ThemePreviews
@Composable
private fun NavigationPreview() {
    NooraTheme {
        NooraNavigationSuitScaffold {}
    }
}