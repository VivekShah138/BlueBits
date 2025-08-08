package com.example.bluebits.presentation.features.chat.components

import android.R.attr.top
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bluebits.presentation.features.chat.MessageState
import com.example.bluebits.presentation.features.chat.MessageType
import com.example.bluebits.ui.theme.BlueBitsTheme
import com.example.bluebits.utils.formatTime

/**
 * A composable representing a single chat message bubble.
 *
 * @param modifier Modifier to be applied to the outer container.
 * @param message The [MessageState] object containing message data to be displayed.
 * @param onMessageLongClick Callback invoked when the message is long-pressed.
 *
 * @see MessageState
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    message : MessageState = MessageState(),
    onMessageLongClick : () -> Unit = {},
) {
    var showTime by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp) // margin from screen sides
    ) {
        BoxWithConstraints(
            modifier = Modifier.align(
                if (message.isMessageFromMe) Alignment.TopEnd
                else Alignment.TopStart
            )
        ) {
            val boxWithConstraintsScope = this
            val bubbleMaxWidth = maxWidth * 0.75f

            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = if (message.isMessageFromMe) 48f else 0f,
                            bottomEnd = if (message.isMessageFromMe) 0f else 48f
                        )
                    )
                    .background(
                        if (message.isMessageFromMe) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.primaryContainer
                    )
                    .padding(16.dp)
                    .animateContentSize()
                    .combinedClickable(
                        onClick = {
                            showTime = !showTime
                        },
                        onLongClick = onMessageLongClick,
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .widthIn(max = bubbleMaxWidth)
            ) {
                // TEXT MESSAGE
                if (message.messageType == MessageType.TEXT) {
                    Text(
                        text = message.data,
                        color = if (message.isMessageFromMe)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }

                // IMAGE MESSAGE
                if (message.messageType == MessageType.IMAGE) {
                    AsyncImage(
                        model = message.data,
                        contentDescription = "Imported Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 300.dp, height = 300.dp)
                    )
                }

                // TIMESTAMP
                if (showTime) {
                    Text(
                        text = message.timeStamp.formatTime().toString(),
                        color = if (!message.isMessageFromMe)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.inverseOnSurface,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                }
            }
        }
    }


}

@Preview
@PreviewLightDark
@Composable
fun Preview(modifier: Modifier = Modifier) {
    BlueBitsTheme {
        ChatItem(
            message = MessageState(data = "Hello this is a message by me", isMessageFromMe = true)
        )
    }
}