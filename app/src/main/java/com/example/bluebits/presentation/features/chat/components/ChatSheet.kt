package com.example.bluebits.presentation.features.chat.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Forward
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.bluebits.presentation.core_components.MenuItems
import com.example.bluebits.presentation.features.chat.MessageState
import com.example.bluebits.ui.theme.BlueBitsTheme

/**
 * A bottom sheet component that displays a selected chat message along with a list of available actions.
 *
 * @param onDismiss Called when the sheet is dismissed by the user.
 * @param sheetState The state object to control the sheet's visibility and behavior.
 * @param message The [MessageState] object representing the selected message. If null, no message is shown.
 * @param actionList A list of [MenuItems] representing the actions available for the selected message.
 *
 * @see MessageState
 * @see MenuItems
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSheet(
    onDismiss: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    message : MessageState? = null,
    actionList : List<MenuItems> = emptyList<MenuItems>()
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            message?.let{
                Box(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
                ){
                    ChatItem(
                        modifier = if(message.isMessageFromMe) Modifier.align(Alignment.TopEnd) else Modifier.align(Alignment.TopStart),
                        message = message
                    )
                }
            }


            actionList.forEachIndexed { index,menuItem ->
                ActionItem(
                    onClick = {
                        menuItem.onClick()
                    },
                    itemText = menuItem.text,
                    icon = menuItem.icon!!,
                    iconContentDescription = menuItem.iconContentDescription,
                    iconColor = if(index ==4) Color.Red else MaterialTheme.colorScheme.onSurface,
                    textColor = if(index ==4) Color.Red else MaterialTheme.colorScheme.onSurface,
                )
            }


        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Preview
@Composable
fun ChatSheetPreview(modifier: Modifier = Modifier) {
    BlueBitsTheme {
        ChatSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            message = MessageState(
                data ="This is a message",
                isMessageFromMe = false
            ),
            actionList = listOf(
                MenuItems(
                    text = "Star",
                    icon = Icons.Outlined.StarOutline,
                    iconContentDescription = "Star Message",
                    onClick = {}
                ),
                MenuItems(
                    text = "Forward",
                    icon = Icons.AutoMirrored.Outlined.Forward,
                    iconContentDescription = "Forward Message",
                    onClick = {}
                ),
                MenuItems(
                    text = "Delete",
                    icon = Icons.Outlined.Delete,
                    iconContentDescription = "Delete message for you",
                    onClick = {}
                ),
                MenuItems(
                    text = "Copy",
                    icon = Icons.Outlined.CopyAll,
                    iconContentDescription = "Copy Text Icon",
                    onClick = {}
                ),
                MenuItems(
                    text = "Unsend",
                    icon = Icons.Outlined.Replay,
                    iconContentDescription = "Copy Text Icon",
                    onClick = {}
                ),

            )

        )
    }
}

