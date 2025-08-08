package com.example.bluebits.presentation.core_components

import android.R.attr.titleTextStyle
import android.R.attr.top
import android.media.SubtitleData
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.bluebits.ui.theme.BlueBitsTheme
import com.example.bluebits.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showSubTitle : Boolean = false,
    subTitle : String = "Connected",
    showBackButton: Boolean = false,
    showMenu: Boolean = false,
    menuItems: List<MenuItems> = emptyList(),
    onBackClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    titleContentColor: Color = MaterialTheme.colorScheme.onBackground,
    navigationIconContentColor: Color = MaterialTheme.colorScheme.onBackground,
    showDivider: Boolean = true,
    showDisplayPic : Boolean = false,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    subTitleTextStyle : TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
    ),
    showOtherAction : Boolean = false,
    @DrawableRes otherActionIcon : Int = R.drawable.ic_location,
    onOtherActionClick : ()->Unit = {},
    otherActionContentDescription : String = "icon"
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = title,
                        style = titleTextStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .then(
                                if(showSubTitle) Modifier.padding(0.dp)
                                else Modifier.padding(vertical = 5.dp)
                            )
                    )
                    if(showSubTitle){
                        Text(
                            text = subTitle,
                            style = subTitleTextStyle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
                if(showDisplayPic){
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "Display picture",
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                        ,
                        contentScale = ContentScale.Crop
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor,
                titleContentColor = titleContentColor,
                navigationIconContentColor = navigationIconContentColor,
            ),
            actions = {
                if (showMenu && menuItems.isNotEmpty()) {

                    var menuExpanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        menuItems.forEach { menuItem ->
                            DropdownMenuItem(
                                text = { Text(menuItem.text) },
                                onClick = {
                                    menuExpanded = false
                                    menuItem.onClick()
                                }
                            )
                        }
                    }
                }
                // actions other than menu
                if(showOtherAction){
                    IconButton(
                        onClick = {
                            onOtherActionClick()
                        }
                    ) {
                        Icon(
                            painter = painterResource(otherActionIcon),
                            contentDescription = otherActionContentDescription
                        )
                    }
                }
            }
        )
        if (showDivider) {
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreviewScreen() {

    BlueBitsTheme  {

        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Title",
                    showBackButton = false,
                    onBackClick = {},
                    showMenu = false
                )
            },
            bottomBar = {
                BottomNavigationBar(rememberNavController())
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

            }
        }
    }
}