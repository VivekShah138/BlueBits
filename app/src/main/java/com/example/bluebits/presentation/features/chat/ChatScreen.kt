package com.example.bluebits.presentation.features.chat

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.Forward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.bluebits.presentation.core_components.AppTopBar
import com.example.bluebits.R
import com.example.bluebits.presentation.core_components.MenuItems
import com.example.bluebits.presentation.features.chat.components.ChatItem
import com.example.bluebits.presentation.features.chat.components.ChatSheet
import com.example.bluebits.presentation.features.chat.components.MessageBox
import com.example.bluebits.presentation.features.chat.components.MessageInputBar
import com.example.bluebits.ui.theme.BlueBitsTheme
import com.example.bluebits.utils.mapToType
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


private const val TAG = "ChatScreen"

@Composable
fun ChatRoot(
    viewModel: ChatViewModel = koinViewModel(),
    navController: NavHostController
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    state : ChatState,
    onEvent : (ChatEvents) -> Unit,
) {
    val context = LocalContext.current
    val clipBoardManager = LocalClipboard.current
    var showSheet  by remember { mutableStateOf(false) }
    var showMediaMenu by remember { mutableStateOf(false) }
    var currentItem by remember { mutableStateOf<MessageState?>(null) }
    var inputText by rememberSaveable { mutableStateOf("") }
    val messageListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // launchers
    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if(uri!=null && uri.mapToType(context) == MessageType.IMAGE){
            onEvent(ChatEvents.ImageMessageSend(uri))
        }
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            //show in ui after picture is saved
        }
    }
    val mediaItems  = listOf<MenuItems>(
        MenuItems(
            text = "Gallery",
            onClick = {
                mediaPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                )
                showMediaMenu = false
            },
            icon = Icons.Outlined.Image
        ),
        MenuItems(
            text = "Document",
            onClick = {},
            // changes this
            icon = Icons.Outlined.FileCopy,
        )
    )


    Scaffold(
        topBar = {
            AppTopBar(
                title = "User",
                showBackButton = false,
                showDisplayPic = true,
                showSubTitle = true,
                subTitle = "Connected",
                showDivider = true,
                showOtherAction = true,
                otherActionIcon = R.drawable.ic_location,
                otherActionContentDescription = "location icon"
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .imePadding()
        ) {


            MessageBox(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                messageListState = messageListState,
                messages = state.messages,
                onMessageLongClick = {
                    showSheet = true
                    currentItem = it
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical= 5.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
            MessageInputBar(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                startIcon = Icons.Default.Add,
                startIconContentDescription = "Add Media",
                startIconModifier = Modifier.padding(5.dp),
                onStartIconClick = {
                    showMediaMenu = true
                },
                endIcon = Icons.AutoMirrored.Filled.Send,
                endIconContentDescription = "Send Message Icon",
                endIconBackgroundColor = MaterialTheme.colorScheme.primary,
                endIconColor = MaterialTheme.colorScheme.onPrimary,
                endIconModifier = Modifier.padding(start = 5.dp,end = 5.dp),
                onEndIconClick = { input ->
                    onEvent(ChatEvents.TextMessageSent(input))

                },
                onInputTextSend = { input ->
                    onEvent(ChatEvents.TextMessageSent(input))
                },
                inputText = inputText,
                onInputTextChange = {input ->
                    inputText = input
                },
                onTrailingIconClick = {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraLauncher.launch(cameraIntent)
                }
            )
        }

    }




    if(showSheet && currentItem!=null){
        ChatSheet(
            onDismiss = {
                showSheet = false
            },
            message = currentItem!!,
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
                    onClick = {
                        onEvent(ChatEvents.MessageDelete(currentItem!!))
                        showSheet = false
                    }
                ),
                MenuItems(
                    text = "Copy",
                    icon = Icons.Outlined.CopyAll,
                    iconContentDescription = "Copy Text Icon",
                    onClick = {
                        scope.launch {
                            if(currentItem!!.messageType == MessageType.TEXT){
                                val clipData = ClipData.newPlainText("TEXT_MESSAGE",currentItem!!.data)
                                clipBoardManager.setClipEntry(clipData.toClipEntry())
                                Toast.makeText(context,"Message Copied", Toast.LENGTH_SHORT).show()
                            }


                        }

                    }
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
    if(showMediaMenu){
        ChatSheet(
            onDismiss = {
                showMediaMenu = false
            },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            actionList = mediaItems
        )
    }
    LaunchedEffect(state.messages) {
        scope.launch {
            messageListState.animateScrollToItem(state.messages.size)
        }
    }




}

@PreviewLightDark
@Preview
@Composable
private fun Preview() {
    BlueBitsTheme {
        ChatScreen(
            state = ChatState(
                messages = listOf(
                    MessageState(data = "How are you", isMessageFromMe = true),
                    MessageState(data = "I am good", isMessageFromMe = false),
                    MessageState(data = "thats good to hear", isMessageFromMe = true)
                )
            ),
            onEvent = {}
        )


    }
}

