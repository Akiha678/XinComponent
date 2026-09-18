package com.seanchen.widget.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * 弹窗
 */
@Composable
fun AppDialog(
    title: String,
    content: String? = null,
    okText: String = "确定",
    cancelText: String = "取消",
    okColor: Color = Color.Unspecified,
    onOk: () -> Unit,
    onCancel: (() -> Unit)? = null,
    onDismiss: () -> Unit,
    confirmEnabled: Boolean = true,
    confirmLoading: Boolean = false,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    val confirmColor = if (okColor == Color.Unspecified) MaterialTheme.colorScheme.primary else okColor

    AppDialog(
        onDismiss = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress && !confirmLoading,
            dismissOnClickOutside = dismissOnClickOutside && !confirmLoading,
            usePlatformDefaultWidth = false,
        ),
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        },
        content = content?.let { body ->
            {
                Text(
                    text = body,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        },
        dismissButton = onCancel?.let { cancel ->
            {
                TextButton(
                    onClick = cancel,
                    enabled = !confirmLoading,
                ) {
                    Text(cancelText)
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onOk,
                enabled = confirmEnabled && !confirmLoading,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = okText,
                        modifier = Modifier.alpha(if (confirmLoading) 0f else 1f),
                        color = if (confirmEnabled) confirmColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    )
                    if (confirmLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = confirmColor,
                            strokeWidth = 2.dp,
                        )
                    }
                }
            }
        },
    )
}

/** Slot-based dialog API for product-specific content while retaining library layout rules. */
@Composable
fun AppDialog(
    onDismiss: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties,
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .widthIn(max = DialogMaxWidth),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 6.dp,
            shadowElevation = 8.dp,
        ) {
            Column(modifier = Modifier.padding(top = 28.dp, start = 24.dp, end = 24.dp, bottom = 12.dp)) {
                title?.invoke()
                if (title != null && content != null) Spacer(Modifier.height(16.dp))
                content?.invoke()
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}

private val DialogMaxWidth = 560.dp
