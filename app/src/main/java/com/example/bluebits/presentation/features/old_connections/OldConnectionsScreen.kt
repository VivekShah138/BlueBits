package com.example.bluebits.presentation.features.old_connections

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bluebits.domain.repository.BluetoothManager
import com.example.bluebits.presentation.core_components.AppTopBar
import com.example.bluebits.presentation.core_components.BottomNavigationBar
import com.example.bluebits.presentation.features.old_connections.components.AvailableDevices
import com.example.bluebits.presentation.features.old_connections.components.BluetoothHandler
import com.example.bluebits.presentation.features.old_connections.components.LifecycleHandler
import com.example.bluebits.ui.theme.BlueBitsTheme
import org.koin.compose.viewmodel.koinViewModel
import com.example.bluebits.presentation.features.old_connections.components.PermissionDialogs
import com.example.bluebits.utils.BluetoothState
import com.example.bluebits.utils.DialogType
import com.example.bluebits.R
import com.example.bluebits.navigation.core.Screens
import com.example.bluebits.presentation.core_components.MenuItems
import com.example.bluebits.utils.createSettingsIntent
import com.example.bluebits.utils.requestMultiplePermissionLogic
import org.koin.compose.koinInject

@Composable
fun OldConnectionsRoot(
    viewModel: OldConnectionsViewModel = koinViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val bluetoothManager: BluetoothManager = koinInject()
    val context = LocalContext.current


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        results.forEach { permission, isGranted ->
            if (!isGranted) {
                viewModel.onEvent(OldConnectionsEvents.AllRationaleVisibility(isVisible = true))
            }
        }
    }

    // Lifecycle handling
    LifecycleHandler(
        onStop = { viewModel.onEvent(OldConnectionsEvents.OnScreenStop) },
        permissionLauncher = permissionLauncher,
        context = context,
        onBluetoothGranted = {
            viewModel.onEvent(OldConnectionsEvents.RequestBluetoothEnable)
        },
        onRationale = {
            viewModel.onEvent(OldConnectionsEvents.AllRationaleVisibility(isVisible = true))
        },
        onLocationRationale = {
            viewModel.onEvent(OldConnectionsEvents.LocationRationaleVisibility(isVisible = true))
        },
        onBluetoothRationale = {
            viewModel.onEvent(OldConnectionsEvents.BluetoothRationaleVisibility(isVisible = true))
        },
        onNotificationsRationaleDialog = {
            viewModel.onEvent(OldConnectionsEvents.NotificationRationaleVisibility(isVisible = true))
        }
    )


    // Bluetooth handling
    BluetoothHandler(
        bluetoothManager = bluetoothManager,
        shouldEnable = state.bluetoothState == BluetoothState.Enabling,
        onBluetoothResult = { enabled ->
            viewModel.onEvent(OldConnectionsEvents.OnBluetoothEnableResult(enabled))
            if (!enabled) {
                viewModel.onEvent(
                    OldConnectionsEvents.BluetoothEnableRationaleVisibility(isVisible = true)
                )
            }
        }
    )

    // Show dialogs
    if (state.dialogState.hasAnyDialog) {
        PermissionDialogs(
            dialogState = state.dialogState,
            onDismiss = { type ->
                when (type) {
                    DialogType.Bluetooth -> viewModel.onEvent(
                        OldConnectionsEvents.BluetoothRationaleVisibility(
                            isVisible = false
                        )
                    )

                    DialogType.Location -> viewModel.onEvent(
                        OldConnectionsEvents.LocationRationaleVisibility(
                            isVisible = false
                        )
                    )

                    DialogType.Notification -> viewModel.onEvent(
                        OldConnectionsEvents.NotificationRationaleVisibility(
                            isVisible = false
                        )
                    )

                    DialogType.All -> viewModel.onEvent(
                        OldConnectionsEvents.NotificationRationaleVisibility(
                            isVisible = false
                        )
                    )

                    DialogType.BluetoothEnable -> viewModel.onEvent(
                        OldConnectionsEvents.BluetoothEnableRationaleVisibility(
                            isVisible = false
                        )
                    )
                }
            },
            onConfirm = { context.createSettingsIntent() },
            onBluetoothEnable = {
                viewModel.onEvent(OldConnectionsEvents.BluetoothEnableRationaleVisibility(isVisible = false))
                viewModel.onEvent(OldConnectionsEvents.RequestBluetoothEnable)
            }
        )
    }

    OldConnectionsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController,
        onStartDiscovery = {
            requestMultiplePermissionLogic(
                context = context,
                launcher = permissionLauncher,
                onBluetoothPermissionGranted = {
                    viewModel.onEvent(OldConnectionsEvents.RequestBluetoothEnable)
                },
                onRationale = {
                    viewModel.onEvent(OldConnectionsEvents.AllRationaleVisibility(isVisible = true))
                },
                onNotificationsRationaleDialog = {
                    viewModel.onEvent(OldConnectionsEvents.NotificationRationaleVisibility(isVisible = true))
                },
                onBluetoothRationale = {
                    viewModel.onEvent(OldConnectionsEvents.BluetoothRationaleVisibility(isVisible = true))
                },
                onLocationRationale = {
                    viewModel.onEvent(OldConnectionsEvents.LocationRationaleVisibility(isVisible = true))
                }
            )
        }
    )
}

@Composable
fun OldConnectionsScreen(
    navController : NavHostController,
    state: OldConnectionsStates,
    onEvent: (OldConnectionsEvents) -> Unit,
    onStartDiscovery: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        },
        topBar = {
            AppTopBar(
                title = "Old Connections",
                showMenu = true,
                menuItems = listOf(
                    MenuItems(
                        text = "Chats",
                        onClick = {
                            navController.navigate(Screens.ChatScreen)
                        }
                    )

                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            AvailableDevices(
                onRefreshClicked = onStartDiscovery,
                devices = state.discoverDeviceList,
                isScanning = state.isScanning
            )
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
            navController = rememberNavController(),
            onStartDiscovery = {}
        )
    }
}



