package com.seanchen.widget.ui.banner

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R

enum class BannerType {
    INFO,
    WARNING,
    ERROR,
    SUCCESS
}

/**
 * 通用提示横幅组件
 *
 * @param message 提示消息内容
 * @param modifier 修饰符
 * @param type 横幅类型（ERROR/WARNING/INFO/SUCCESS）
 * @param onClose 点击关闭回调，为空时不显示关闭按钮
 * @param leadingIcon 可选自定义前置图标
 */
@Composable
fun AppBanner(
    message: String,
    modifier: Modifier = Modifier,
    type: BannerType = BannerType.ERROR,
    onClose: (() -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    val (containerColor, contentColor) = when (type) {
        BannerType.ERROR -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.85f) to MaterialTheme.colorScheme.onErrorContainer
        BannerType.WARNING -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.85f) to MaterialTheme.colorScheme.onTertiaryContainer
        BannerType.INFO -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f) to MaterialTheme.colorScheme.onSurfaceVariant
        BannerType.SUCCESS -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f) to MaterialTheme.colorScheme.onPrimaryContainer
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.invoke()

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )

            if (onClose != null) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Close",
                        modifier = Modifier.size(16.dp),
                        tint = contentColor
                    )
                }
            }
        }
    }
}
