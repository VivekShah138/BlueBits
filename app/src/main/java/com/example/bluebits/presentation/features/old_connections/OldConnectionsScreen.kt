package com.example.bluebits.presentation.features.old_connections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bluebits.presentation.core_components.AppTopBar
import com.example.bluebits.presentation.core_components.BottomNavigationBar
import com.example.bluebits.ui.theme.BlueBitsTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OldConnectionsRoot(
    viewModel: OldConnectionsViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OldConnectionsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}

@Composable
fun OldConnectionsScreen(
    navController: NavController,
    state: OldConnectionsStates,
    onEvent: (OldConnectionsEvents) -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        },
        topBar = {
            AppTopBar(
                title = "Old Connections"
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Old Connections Screen")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BlueBitsTheme {
        OldConnectionsScreen(
            state = OldConnectionsStates(),
            onEvent = {},
            navController = rememberNavController()
        )
    }
}