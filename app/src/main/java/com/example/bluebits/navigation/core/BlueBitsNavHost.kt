package com.example.bluebits.navigation.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bluebits.navigation.oldConnectionsGraph
import com.example.bluebits.navigation.settingsGraph
import com.example.bluebits.navigation.splashGraph

@Composable
fun BlueBitsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screens = Screens.OldConnectionScreen
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        splashGraph(
            navController = navController
        )

        oldConnectionsGraph(
            navController = navController
        )

        settingsGraph(
            navController = navController
        )
    }
}