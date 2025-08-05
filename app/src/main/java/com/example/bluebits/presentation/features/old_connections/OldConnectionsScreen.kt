package com.example.bluebits.presentation.features.old_connections

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.bluebits.presentation.features.old_connections.components.AvailableDevices
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
    var showLocationRationaleDialog by remember { mutableStateOf(false) }

    var turnOnBluetoothRationaleDialog by remember { mutableStateOf(false) }
    var bluetoothEnableRequested by remember { mutableStateOf(false) }
    var bluetoothEnableDenied by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        results.forEach { permission, isGranted ->
            if (!isGranted) {
                showAllRationaleDialog = true
            }
        }
    }

    val bluetoothEnableLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        bluetoothEnableRequested = false

        if (result.resultCode == Activity.RESULT_OK) {
            bluetoothEnableDenied = false
            viewModel.onEvent(OldConnectionsEvents.StartDeviceDiscovery)
        } else {
            bluetoothEnableDenied = true
            turnOnBluetoothRationaleDialog = true
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
                        context = context,
                        launcher = permissionLauncher,
                        onBluetoothPermissionGranted = {
                            showBluetoothRationaleDialog = false
                            showAllRationaleDialog = false
                            showNotificationsRationaleDialog = false

                            if (state.isBluetoothSupported  && !state.isBluetoothEnabled) {
                                when {
                                    !bluetoothEnableRequested && !bluetoothEnableDenied -> {
                                        bluetoothEnableRequested = true
                                        val enableBtIntent =
                                            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                        bluetoothEnableLauncher.launch(enableBtIntent)
                                    }

                                    bluetoothEnableDenied -> {
                                        turnOnBluetoothRationaleDialog = true
                                    }
                                }
                            } else if (state.isBluetoothSupported && state.isBluetoothEnabled) {
                                viewModel.onEvent(OldConnectionsEvents.StartDeviceDiscovery)
                            }
                        },
                        onRationale = {
                            showAllRationaleDialog = true
                        },
                        onNotificationsRationaleDialog = {
                            showNotificationsRationaleDialog = true
                        },
                        onBluetoothRationale = {
                            showBluetoothRationaleDialog = true
                        },
                        onLocationRationale = {
                            showLocationRationaleDialog = true
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

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                Log.d("OldConnectionsRoot", "Screen stopped, stopping discovery")
                viewModel.onEvent(OldConnectionsEvents.StopDeviceDiscovery)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val onStartDiscovery = {
        requestMultiplePermissionLogic(
            context = context,
            launcher = permissionLauncher,
            onBluetoothPermissionGranted = {
                showBluetoothRationaleDialog = false
                showAllRationaleDialog = false
                showNotificationsRationaleDialog = false

                val bluetoothManager =
                    context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                val bluetoothAdapter = bluetoothManager.adapter

                if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled) {
                    if (!bluetoothEnableRequested && !bluetoothEnableDenied) {
                        bluetoothEnableRequested = true
                        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        bluetoothEnableLauncher.launch(enableBtIntent)
                    } else if (bluetoothEnableDenied) {
                        turnOnBluetoothRationaleDialog = true
                    }
                } else {
                    viewModel.onEvent(OldConnectionsEvents.StartDeviceDiscovery)
                }

            },
            onRationale = {
                showAllRationaleDialog = true
            },
            onNotificationsRationaleDialog = {
                showNotificationsRationaleDialog = true
            },
            onBluetoothRationale = {
                showBluetoothRationaleDialog = true
            },
            onLocationRationale = {
                showBluetoothRationaleDialog = true
            }
        )
    }

    // Show rationale dialogs if needed
    PermissionDialogs(
        showBluetoothRationaleDialog = showBluetoothRationaleDialog,
        showNotificationsRationaleDialog = showNotificationsRationaleDialog,
        turnOnBluetoothRationaleDialog = turnOnBluetoothRationaleDialog,
        showLocationRationaleDialog = showLocationRationaleDialog,
        showAllRationaleDialog = showAllRationaleDialog,
        onDismissBluetooth = { showBluetoothRationaleDialog = false },
        onDismissNotifications = { showNotificationsRationaleDialog = false },
        onDismissTurnOnBluetooth = { turnOnBluetoothRationaleDialog = false },
        onDismissLocation = { showLocationRationaleDialog = false},
        onDismissAll = { showAllRationaleDialog = false },
        onConfirm = { context.createSettingsIntent() },
        onBluetoothEnable = {
            bluetoothEnableDenied = false
            bluetoothEnableRequested = true
            val bluetoothManager =
                context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter
            if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                bluetoothEnableLauncher.launch(enableBtIntent)
            } else {
                viewModel.onEvent(OldConnectionsEvents.StartDeviceDiscovery)
            }

            turnOnBluetoothRationaleDialog = false
        }
    )


    OldConnectionsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController,
        onStartDiscovery = onStartDiscovery
    )
}

@Composable
fun OldConnectionsScreen(
    navController: NavController,
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
            AvailableDevices(
                onRefreshClicked = onStartDiscovery,
                devices = state.discoverDeviceList
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



