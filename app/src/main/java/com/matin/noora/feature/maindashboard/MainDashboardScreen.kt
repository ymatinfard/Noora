package com.matin.noora.feature.maindashboard

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.matin.noora.core.common.TopLevelDestination
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.feature.chat.ChatDashboardScreenRoute
import com.matin.noora.feature.home.HomeScreenRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NooraMainDashboardRoute(
    onChatCharacterClicked: (ChatCharacterItem) -> Unit
) {

    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { TopLevelDestination.entries.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(WindowInsets.safeDrawing),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                TopLevelDestination.entries.forEachIndexed { index, destination ->
                    val selected = pagerState.currentPage == index
                    NooraNavigationBarItem(scope, pagerState, index, selected, destination)
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier.padding(innerPadding),
            state = pagerState,
            userScrollEnabled = false) { page ->

            val targetAlpha = if (pagerState.currentPage == page) 1f else 0f
            val pageAlpha by animateFloatAsState(targetValue = targetAlpha)

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = pageAlpha
                    }
                    .fillMaxSize()
            ) {
                when (TopLevelDestination.entries[page]) {
                    TopLevelDestination.Home -> HomeScreenRoute()
                    TopLevelDestination.ChatDashboard -> ChatDashboardScreenRoute(
                        onChatCharacterClicked = onChatCharacterClicked
                    )

                    TopLevelDestination.Tools -> HomeScreenRoute()
                }
            }
        }
    }
}

@Composable
private fun RowScope.NooraNavigationBarItem(
    scope: CoroutineScope,
    pagerState: PagerState,
    index: Int,
    selected: Boolean,
    destination: TopLevelDestination
) {
    NavigationBarItem(
        onClick = {
            scope.launch {
                pagerState.scrollToPage(index)
            }
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
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        label = { Text(text = destination.title) },
    )
}