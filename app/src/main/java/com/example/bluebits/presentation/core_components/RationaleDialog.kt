package com.example.bluebits.presentation.core_components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import com.example.bluebits.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun RationaleDialog(
    modifier: Modifier = Modifier,
    title: String = if(LocalInspectionMode.current)"Permission Denied" else "",
    body: String = if(LocalInspectionMode.current)"Go to settings and grant permission" else "",
    icon: Int? = null,
    iconColor : Color = MaterialTheme.colorScheme.primary,
    iconContentDescription: String = "",
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    dismissText: String = stringResource(R.string.dismiss),
    confirmText: String = stringResource(R.string.settings),
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    btnColor: Color = MaterialTheme.colorScheme.primary
) {
    AlertDialog(
        containerColor = containerColor,
        iconContentColor = iconColor,
        icon = {
            icon?.let {
                Icon(
                    modifier = modifier
                        .size(32.dp)
                    ,
                    painter = painterResource(icon),
                    contentDescription = iconContentDescription
                )
            }

        },
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        text = {
            Text(
                text = body,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )

        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                },
                colors = ButtonDefaults.buttonColors(containerColor = btnColor)
            ) {
                Text(
                    text = confirmText
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    onDismiss()
                },
                border = BorderStroke(width = 1.dp,color = MaterialTheme.colorScheme.primary)

            ) {
                Text(
                    text = dismissText,
                    color = btnColor
                )
            }
        }
    )
}