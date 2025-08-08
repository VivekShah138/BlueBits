package com.example.bluebits.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.bluebits.navigation.core.Screens
import com.example.bluebits.presentation.features.chat.ChatRoot
import com.example.bluebits.presentation.features.old_connections.OldConnectionsRoot

fun NavGraphBuilder.chatGraph(
    navController: NavHostController
){
    composable<Screens.ChatScreen> {
        ChatRoot(
            navController = navController
        )
    }

}