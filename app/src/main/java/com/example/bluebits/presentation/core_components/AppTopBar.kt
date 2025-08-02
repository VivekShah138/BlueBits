package com.example.bluebits.presentation.core_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.bluebits.ui.theme.BlueBitsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    showMenu: Boolean = false,
    menuItems: List<MenuItems> = emptyList(),
    onBackClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    titleContentColor: Color = MaterialTheme.colorScheme.onBackground,
    navigationIconContentColor: Color = MaterialTheme.colorScheme.onBackground,
    showDivider: Boolean = true,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = titleTextStyle,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                )
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