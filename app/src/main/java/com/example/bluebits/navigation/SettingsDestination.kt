package com.example.bluebits.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bluebits.navigation.core.Screens
import com.example.bluebits.presentation.features.chat.ChatRoot
import com.example.bluebits.presentation.features.settings.SettingsRoot

fun NavGraphBuilder.settingsGraph(
    navController: NavController
){
    composable<Screens.SettingsScreen> {
        SettingsRoot(
            navController = navController
        )
    }
}