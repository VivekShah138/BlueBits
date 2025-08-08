package com.example.bluebits.presentation.features.chat.components

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.bluebits.ui.theme.BlueBitsTheme

/**
 * A composable that displays a clickable circular icon.
 *
 * @param modifier Modifier to be applied to the icon button container.
 * @param onClick Callback invoked when the icon is clicked.
 * @param icon The [ImageVector] to be displayed.
 * @param iconContentDescription The content description for accessibility services.
 * @param iconColor The color used to tint the [icon].
 * @param backgroundColor The background color of the icon container.
 */
@Composable
fun IconItem(
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {},
    icon : ImageVector,
    iconContentDescription : String,
    iconColor : Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor : Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f),
) {
    IconButton(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconContentDescription,
            tint = iconColor
        )
    }
}

@Preview
@Composable
fun IconItemPreview(modifier: Modifier = Modifier) {
    BlueBitsTheme {
        IconItem(
            modifier = Modifier.padding(5.dp),
            icon  = Icons.Default.Add,
            iconContentDescription = "Add Icon"
        )
    }
}