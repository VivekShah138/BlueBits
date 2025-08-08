package com.example.bluebits.presentation.features.chat.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluebits.presentation.features.chat.ChatEvents
import com.example.bluebits.ui.theme.BlueBitsTheme

/**
 * A composable input bar for composing and sending messages, with customizable start and end icons.
 *
 * @param modifier Modifier to be applied to the input bar container.
 * @param startIcon Icon shown at the beginning of the input bar.
 * @param startIconColor Tint color for the [startIcon].
 * @param startIconBackgroundColor Background color for the [startIcon].
 * @param startIconContentDescription Content description for accessibility services.
 * @param startIconModifier Modifier applied to the [startIcon].
 * @param onStartIconClick Callback invoked when the start icon is clicked.
 * @param endIcon Icon shown at the end of the input bar.
 * @param endIconContentDescription Content description for accessibility services.
 * @param endIconModifier Modifier applied to the [endIcon].
 * @param endIconColor Tint color for the [endIcon].
 * @param endIconBackgroundColor Background color for the [endIcon].
 * @param onEndIconClick Callback invoked when the end icon is clicked with the current [inputText].
 * @param onInputTextSend Callback invoked when the keyboard send action is triggered with the current [inputText].
 * @param inputText The current input text value.
 * @param onInputTextChange Callback invoked when the input text changes.
 * @param onTrailingIconClick Callback invoked when the trailing icon inside the input field is clicked.
 */
@Composable
fun MessageInputBar(
    modifier: Modifier = Modifier,
    startIcon: ImageVector,
    startIconColor: Color = MaterialTheme.colorScheme.onSurface,
    startIconBackgroundColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f),
    startIconContentDescription: String,
    startIconModifier: Modifier = Modifier,
    onStartIconClick: () -> Unit = {},
    endIcon: ImageVector,
    endIconContentDescription: String,
    endIconModifier: Modifier = Modifier,
    endIconColor: Color = MaterialTheme.colorScheme.onSurface,
    endIconBackgroundColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f),
    onEndIconClick: (String) -> Unit = {},
    onInputTextSend: (String) -> Unit = {},
    inputText : String,
    onInputTextChange: (String) -> Unit,
    onTrailingIconClick : () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IconItem(
            icon = startIcon,
            iconContentDescription = startIconContentDescription,
            modifier = startIconModifier,
            onClick = {
                onStartIconClick()
            }
        )
        TextField(
            value = inputText,
            onValueChange = { it ->
               onInputTextChange(it)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    onInputTextSend(inputText)
                    onInputTextChange("")
                }
            ),
            textStyle = TextStyle(fontSize = 18.sp),
            maxLines = 5,
            minLines = 1,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(32.dp)),
            placeholder = {
                Text("Message")
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onTrailingIconClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CameraAlt,
                        contentDescription = "Open Camera"
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
        AnimatedVisibility(inputText.trim().isNotEmpty()) {
            IconItem(
                modifier = endIconModifier,
                icon = endIcon,
                iconContentDescription = endIconContentDescription,
                iconColor = endIconColor,
                backgroundColor = endIconBackgroundColor,
                onClick = {
                    onEndIconClick(inputText)
                    onInputTextChange("")
                }
            )

        }


    }
}

@Preview
@Composable
fun MessageInputBarPreview(modifier: Modifier = Modifier) {
    BlueBitsTheme {
        var mockText by remember { mutableStateOf("") }
        MessageInputBar(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            startIcon = Icons.Default.Add,
            startIconContentDescription = "Add Media",
            startIconModifier = Modifier.padding(5.dp),
            onStartIconClick = {},
            endIcon = Icons.AutoMirrored.Filled.Send,
            endIconContentDescription = "Send Message Icon",
            endIconBackgroundColor = MaterialTheme.colorScheme.primary,
            endIconColor = MaterialTheme.colorScheme.onPrimary,
            endIconModifier = Modifier.padding(start = 5.dp,end = 5.dp),
            inputText = mockText,
            onInputTextChange = {input ->
                mockText = input
            }

        )
    }
}