package com.example.bluebits.presentation.features.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluebits.presentation.features.chat.MessageState
import com.example.bluebits.ui.theme.BlueBitsTheme

/**
 * A composable that displays a scrollable list of chat messages.
 *
 * @param modifier Modifier to be applied to the message list container.
 * @param messageListState The scroll state of the message list.
 * @param messages The list of [MessageState] items to display.
 * @param onMessageLongClick Callback invoked when a message is long-pressed.
 *
 * @see MessageState
 * @see ChatItem
 */
@Composable
fun MessageBox(
    modifier: Modifier = Modifier,
    messageListState : LazyListState,
    messages : List<MessageState>,
    onMessageLongClick : (MessageState) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = false,
        state = messageListState
    ) {
        // messages
        items(
            messages,
            key = { it ->
                it.messageId
            }
        ) { it ->
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth()
                    .animateItem()
            ){
                // specific message chat ui
                ChatItem(
                    modifier = if(it.isMessageFromMe) Modifier.align(Alignment.TopEnd) else Modifier.align(Alignment.TopStart),
                    message = it,
                    onMessageLongClick = {
                        onMessageLongClick(it)
                    }
                )

            }
        }

    }

}



