package com.seanchen.widget.ui.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    isFocused: Boolean = false,
    color: Color = MaterialTheme.colorScheme.outline,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    thickness: Dp = 0.5.dp
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = if (isFocused) focusedColor else color
    )
}
