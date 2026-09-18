package com.seanchen.widget.ui.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seanchen.widget.ui.theme.ColorDanger
import com.seanchen.widget.ui.theme.TextWhite

/**
 * 格式化徽标计数字符串。
 *
 * @param count 数量
 * @param maxCount 最大值，超过该值则显示 "$maxCount+"
 * @param showZero 是否展示 0
 * @return 格式化后的字符串，如果不需要展示则返回 null
 */
fun formatBadgeCount(
    count: Int,
    maxCount: Int = 99,
    showZero: Boolean = false
): String? {
    if (count < 0) return null
    if (count == 0 && !showZero) return null
    return if (count > maxCount) "$maxCount+" else count.toString()
}

/**
 * 徽标组件，用于消息数、红点提醒、标签标注等。
 *
 * 既可以独立展示，也可以包裹在任意内容（如 Icon/Button）外层自动定位在右上角。
 *
 * @param modifier 修饰符
 * @param count 数字计数，大于 maxCount 时截断展示（如 99+）
 * @param maxCount 最大展示数字，默认为 99
 * @param showZero 为 0 时是否展示，默认为 false
 * @param text 文本徽标，优先级低于 isDot，高于 count
 * @param isDot 是否为纯红点模式
 * @param containerColor 徽标背景色，默认为危险红色
 * @param contentColor 徽标文字颜色，默认为白色
 * @param offset 当包裹内容时的右上角偏移量
 * @param content 宿主内容组件，为 null 时独立展示徽标
 */
@Composable
fun AppBadge(
    modifier: Modifier = Modifier,
    count: Int? = null,
    maxCount: Int = 99,
    showZero: Boolean = false,
    text: String? = null,
    isDot: Boolean = false,
    containerColor: Color = ColorDanger,
    contentColor: Color = TextWhite,
    offset: DpOffset = DpOffset(x = 6.dp, y = (-6).dp),
    content: (@Composable () -> Unit)? = null
) {
    val displayContent = when {
        isDot -> ""
        !text.isNullOrEmpty() -> text
        count != null -> formatBadgeCount(count, maxCount, showZero)
        else -> null
    }

    val shouldDisplay = isDot || displayContent != null

    val badgeElement = @Composable {
        if (shouldDisplay) {
            if (isDot) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(containerColor)
                )
            } else if (displayContent != null) {
                Box(
                    modifier = Modifier
                        .heightIn(min = 18.dp)
                        .widthIn(min = 18.dp)
                        .clip(CircleShape)
                        .background(containerColor)
                        .padding(horizontal = 5.dp, vertical = 1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = displayContent,
                        color = contentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 11.sp
                    )
                }
            }
        }
    }

    if (content == null) {
        // 独立展示
        Box(
            modifier = modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            badgeElement()
        }
    } else {
        // 附着在宿主右上角展示
        Box(
            modifier = modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
            if (shouldDisplay) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = offset.x, y = offset.y)
                ) {
                    badgeElement()
                }
            }
        }
    }
}
