package com.example.bluebits.navigation.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class BlueBitsTopLevelDestination(
    private val navController: NavController
){
    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.screen) {

            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destination on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true

            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}


data class TopLevelDestination(
    val title: String,
    val screen: Screens,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val TOP_LEVEL_DESTINATION = listOf(
    TopLevelDestination(
        title = "Home",
        screen = Screens.OldConnectionScreen,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    TopLevelDestination(
        title = "Settings",
        screen = Screens.SettingsScreen,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)
