package com.example.bluebits.presentation.features.old_connections

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bluebits.presentation.core_components.AppTopBar
import com.example.bluebits.presentation.core_components.BottomNavigationBar
import com.example.bluebits.ui.theme.BlueBitsTheme
import org.koin.compose.viewmodel.koinViewModel
import com.example.bluebits.presentation.features.old_connections.components.PermissionDialogs
import com.example.bluebits.utils.createSettingsIntent
import com.example.bluebits.utils.requestMultiplePermissionLogic


@Composable
fun OldConnectionsRoot(
    viewModel: OldConnectionsViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showBluetoothRationaleDialog by remember { mutableStateOf(false) }
    var showAllRationaleDialog by remember { mutableStateOf(false) }
    var showNotificationsRationaleDialog by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        results.forEach { permission, isGranted ->
            if(!isGranted){
                showAllRationaleDialog = true
            }
        }
    }

    DisposableEffect(Unit) {
        val observer = object : LifecycleEventObserver {
            override fun onStateChanged(
                source: LifecycleOwner,
                event: Lifecycle.Event
            ) {
                if (event == Lifecycle.Event.ON_START) {
                    requestMultiplePermissionLogic(
                        context,
                        permissionLauncher,
                        onBluetoothPermissionGranted = {
                            showBluetoothRationaleDialog = false
                            showAllRationaleDialog = false
                            showNotificationsRationaleDialog = false
                        },
                        onRationale = {
                            showAllRationaleDialog = true
                        },
                        onNotificationsRationaleDialog = {
                            showNotificationsRationaleDialog = true
                        },
                        onBluetoothRationale = {
                            showBluetoothRationaleDialog = true
                        }
                    )
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Show rationale dialogs if needed
    PermissionDialogs(
        showBluetoothRationaleDialog = showBluetoothRationaleDialog,
        showNotificationsRationaleDialog = showNotificationsRationaleDialog,
        showAllRationaleDialog = showAllRationaleDialog,
        onDismissBluetooth = { showBluetoothRationaleDialog = false },
        onDismissNotifications = { showNotificationsRationaleDialog = false },
        onDismissAll = { showAllRationaleDialog = false },
        onConfirm = { context.createSettingsIntent() }
    )


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



