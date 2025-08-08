package com.example.bluebits.presentation.core_components

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItems(
    val text: String,
    val onClick: () -> Unit,
    val icon : ImageVector? = null,
    val iconContentDescription : String = "Icon"
)
