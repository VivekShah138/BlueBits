package com.example.bluebits.presentation.features.old_connections

import android.Manifest
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bluebits.presentation.core_components.AppTopBar
import com.example.bluebits.presentation.core_components.BottomNavigationBar
import com.example.bluebits.presentation.core_components.RationaleDialog
import com.example.bluebits.ui.theme.BlueBitsTheme
import com.example.bluebits.utils.isPermisssionGranted
import org.koin.compose.viewmodel.koinViewModel
import com.example.bluebits.R
import com.example.bluebits.utils.createSettingsIntent

const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
const val BLUETOOTH_SCAN = Manifest.permission.BLUETOOTH_SCAN
const val BLUETOOTH_ADVERTISE = Manifest.permission.BLUETOOTH_ADVERTISE
const val BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
const val TAG = "OldConnectionsScreen"

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

    if (showBluetoothRationaleDialog) {
        RationaleDialog(
            title = "Permission Denied",
            body = "You wont able to use bluetooth features in this app. " +
                    "Click on settings and grant permission which are revoked",
            onDismiss = {
                showBluetoothRationaleDialog = false
            },
            onConfirm = {
                context.createSettingsIntent()
            },
            // have to change this to app icon
            icon = R.drawable.ic_bluetooth,
            iconColor = MaterialTheme.colorScheme.primary,
            iconContentDescription = "App Icon"
        )
    }

    if (showNotificationsRationaleDialog) {
        RationaleDialog(
            title = "Permission Denied",
            body = "You wont able to use notifications features in this app. " +
                    "Click on settings and grant permission which are revoked",
            onDismiss = {
                showNotificationsRationaleDialog = false
            },
            onConfirm = {
                context.createSettingsIntent()
            },
            // have to change this to app icon
            icon = R.drawable.ic_bluetooth,
            iconColor = MaterialTheme.colorScheme.primary,
            iconContentDescription = "App Icon"
        )
    }

    if (showAllRationaleDialog) {
        RationaleDialog(
            title = "Permission Denied",
            body = "You wont able to use Bluetooth/notifications features in this app. " +
                    "Click on settings and grant permission which are revoked",
            onDismiss = {
                showAllRationaleDialog = false
            },
            onConfirm = {
                context.createSettingsIntent()
            },
            // have to change this to app icon
            icon = R.drawable.ic_bluetooth,
            iconColor = MaterialTheme.colorScheme.primary,
            iconContentDescription = "App Icon"
        )
    }



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


private fun requestMultiplePermissionLogic(
    context: Context,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    onBluetoothPermissionGranted: () -> Unit,
    onRationale: () -> Unit,
    onBluetoothRationale : () -> Unit,
    onNotificationsRationaleDialog: () -> Unit
) {
    val activity = context as Activity

    val bluetoothRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_SCAN) &&
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    BLUETOOTH_ADVERTISE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_CONNECT)
    val notificationRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,POST_NOTIFICATIONS)
    when {

        context.run {
            isPermisssionGranted(BLUETOOTH_SCAN) && isPermisssionGranted(BLUETOOTH_ADVERTISE) &&
                    isPermisssionGranted(BLUETOOTH_CONNECT)
        } -> {
            onBluetoothPermissionGranted()
            if (!context.isPermisssionGranted(POST_NOTIFICATIONS)) {
                onNotificationsRationaleDialog()
            }
            Log.i(TAG, "requestMultiplePermissionLogic:  Permission has been granted ")
        }

        bluetoothRationale && notificationRationale -> {
            onRationale()
        }

//        notificationRationale -> {
//            onNotificationsRationaleDialog()
//        }
        bluetoothRationale -> {
            onBluetoothRationale()
        }



        else -> {
            launcher.launch(
                arrayOf(
                    BLUETOOTH_SCAN,
                    BLUETOOTH_ADVERTISE,
                    BLUETOOTH_CONNECT,
                    POST_NOTIFICATIONS
                )
            )
        }
    }

}








