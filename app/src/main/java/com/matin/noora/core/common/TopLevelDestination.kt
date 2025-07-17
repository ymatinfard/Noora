package com.matin.noora.core.common

import com.matin.noora.R
import com.matin.noora.feature.chat.navigation.ChatDashboardRoute
import com.matin.noora.feature.home.navigation.HomeRoute
import com.matin.noora.feature.tools.navigation.ToolsRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(val title: String, val icon: Int, val route: KClass<*>) {
    Home("Home", R.drawable.ic_home,  HomeRoute::class),
    ChatDashboard("Chat", R.drawable.ic_chat, ChatDashboardRoute::class),
    Tools("Profile", R.drawable.ic_tools, ToolsRoute::class)
}