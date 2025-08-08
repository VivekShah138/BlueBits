package com.example.bluebits.presentation.features.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluebits.ui.theme.BlueBitsTheme

/**
 * A composable representing an individual action item with an icon and label.
 *
 * @param onClick Callback invoked when the item is clicked.
 * @param itemText The label text displayed next to the icon.
 * @param textColor The color of the [itemText].
 * @param icon The icon to be displayed.
 * @param iconColor The color used to tint the [icon].
 * @param iconContentDescription The content description for accessibility services.
 * @param backgroundColor The background color of the row.
 */
@Composable
fun ActionItem(
    onClick: () -> Unit = {},
    itemText : String = "Text",
    textColor : Color = MaterialTheme.colorScheme.onSurface,
    icon : ImageVector = Icons.Outlined.Delete,
    iconColor : Color = MaterialTheme.colorScheme.onSurface,
    iconContentDescription : String = "Icon Description",
    backgroundColor : Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.1f)
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = { onClick() }
            )
            .padding(vertical = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier
                    .size(36.dp)
                    .padding(5.dp),
                tint = iconColor
            )
            Text(
                text = itemText,
                modifier = Modifier.padding(15.dp),
                color = textColor
            )
        }
    }
}

@Preview
@Composable
fun ActionItemPreview(modifier: Modifier = Modifier) {
    BlueBitsTheme {
        ActionItem(

        )
    }
}