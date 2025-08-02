package com.example.bluebits.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bluebits.navigation.core.Screens
import com.example.bluebits.presentation.features.old_connections.OldConnectionsRoot

fun NavGraphBuilder.oldConnectionsGraph(
    navController: NavController
){
    composable<Screens.OldConnectionScreen> {
        OldConnectionsRoot(
            navController = navController
        )
    }

    composable<Screens.DeviceDiscoveryScreen> {

    }
}