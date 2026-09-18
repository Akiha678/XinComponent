package com.seanchen.widget.ui.empty

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.button.AppButtonFixed
import com.seanchen.widget.ui.button.ButtonSize
import com.seanchen.widget.ui.button.ButtonStyle
import com.seanchen.widget.ui.button.ButtonType
import com.seanchen.widget.ui.theme.SpacePaddingLarge
import com.seanchen.widget.ui.theme.SpaceVerticalLarge
import com.seanchen.widget.ui.theme.SpaceVerticalMedium
import com.seanchen.widget.ui.theme.SpaceVerticalSmall

enum class EmptyType {
    DATA,
    NETWORK,
    ERROR,
    CART,
    CUSTOM
}

/**
 * 空状态展示组件，适用于无数据、网络错误、加载异常、空购物车等场景。
 *
 * @param modifier 修饰符，默认为 Modifier
 * @param type 空状态类型
 * @param image 自定义插图插槽，为 null 时根据 type 自动渲染精美矢量图形
 * @param title 标题文案，为 null 时根据 type 自动从资源中获取
 * @param subtitle 副标题/辅助说明文案，为 null 时根据 type 自动获取
 * @param actionText 底部操作按钮文案，为 null 时根据 type 自动获取；若为空字符串则不展示按钮
 * @param onActionClick 点击操作按钮的回调
 */
@Composable
fun AppEmpty(
    modifier: Modifier = Modifier,
    type: EmptyType = EmptyType.DATA,
    image: (@Composable () -> Unit)? = null,
    title: String? = null,
    subtitle: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    val resolvedTitle = title ?: when (type) {
        EmptyType.DATA -> stringResource(id = R.string.empty_data)
        EmptyType.NETWORK -> stringResource(id = R.string.empty_network)
        EmptyType.ERROR -> stringResource(id = R.string.empty_error)
        EmptyType.CART -> stringResource(id = R.string.empty_cart)
        EmptyType.CUSTOM -> ""
    }

    val resolvedSubtitle = subtitle ?: when (type) {
        EmptyType.DATA -> stringResource(id = R.string.empty_data_subtitle)
        EmptyType.NETWORK -> stringResource(id = R.string.empty_network_subtitle)
        EmptyType.ERROR -> stringResource(id = R.string.empty_error_subtitle)
        EmptyType.CART -> stringResource(id = R.string.empty_cart_subtitle)
        EmptyType.CUSTOM -> ""
    }

    val resolvedActionText = actionText ?: when (type) {
        EmptyType.NETWORK, EmptyType.ERROR -> stringResource(id = R.string.click_retry)
        EmptyType.CART -> stringResource(id = R.string.empty_cart_btn)
        else -> null
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(SpacePaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 插图区域
        if (image != null) {
            image()
        } else {
            DefaultEmptyGraphic(type = type)
        }

        Spacer(modifier = Modifier.height(SpaceVerticalLarge))

        // 标题
        if (resolvedTitle.isNotEmpty()) {
            Text(
                text = resolvedTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }

        // 副标题
        if (resolvedSubtitle.isNotEmpty()) {
            Spacer(modifier = Modifier.height(SpaceVerticalSmall))
            Text(
                text = resolvedSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        // 操作按钮
        if (!resolvedActionText.isNullOrEmpty() && onActionClick != null) {
            Spacer(modifier = Modifier.height(SpaceVerticalLarge))
            AppButtonFixed(
                text = resolvedActionText,
                onClick = onActionClick,
                style = if (type == EmptyType.NETWORK || type == EmptyType.ERROR) ButtonStyle.OUTLINED else ButtonStyle.FILLED,
                type = ButtonType.DEFAULT,
                size = ButtonSize.SMALL
            )
        }
    }
}

/**
 * 默认空状态矢量插图
 */
@Composable
private fun DefaultEmptyGraphic(
    type: EmptyType,
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    surfaceVariant: Color = MaterialTheme.colorScheme.surfaceVariant,
    outlineColor: Color = MaterialTheme.colorScheme.outline
) {
    Box(
        modifier = modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(110.dp)) {
            val w = size.width
            val h = size.height
            val centerX = w / 2f
            val centerY = h / 2f

            // 背景光晕/圆盘
            drawCircle(
                color = surfaceVariant.copy(alpha = 0.6f),
                radius = w * 0.45f,
                center = Offset(centerX, centerY)
            )

            when (type) {
                EmptyType.DATA, EmptyType.CUSTOM -> {
                    // 数据卡片/文档图案
                    val docLeft = centerX - w * 0.22f
                    val docTop = centerY - h * 0.28f
                    val docWidth = w * 0.44f
                    val docHeight = h * 0.56f

                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.85f),
                        topLeft = Offset(docLeft, docTop),
                        size = Size(docWidth, docHeight),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )
                    drawRoundRect(
                        color = outlineColor.copy(alpha = 0.5f),
                        topLeft = Offset(docLeft, docTop),
                        size = Size(docWidth, docHeight),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // 几条横线
                    val lineX = docLeft + docWidth * 0.2f
                    val lineWidth = docWidth * 0.6f
                    drawLine(
                        color = primaryColor.copy(alpha = 0.8f),
                        start = Offset(lineX, docTop + docHeight * 0.3f),
                        end = Offset(lineX + lineWidth, docTop + docHeight * 0.3f),
                        strokeWidth = 3.dp.toPx()
                    )
                    drawLine(
                        color = outlineColor.copy(alpha = 0.6f),
                        start = Offset(lineX, docTop + docHeight * 0.5f),
                        end = Offset(lineX + lineWidth * 0.8f, docTop + docHeight * 0.5f),
                        strokeWidth = 2.5.dp.toPx()
                    )
                    drawLine(
                        color = outlineColor.copy(alpha = 0.4f),
                        start = Offset(lineX, docTop + docHeight * 0.7f),
                        end = Offset(lineX + lineWidth * 0.5f, docTop + docHeight * 0.7f),
                        strokeWidth = 2.5.dp.toPx()
                    )
                }

                EmptyType.NETWORK -> {
                    // Wi-Fi 信号/断网图案
                    val stroke = Stroke(width = 3.dp.toPx())
                    val baseRadius = w * 0.15f

                    // 弧线1
                    drawArc(
                        color = outlineColor.copy(alpha = 0.4f),
                        startAngle = 200f,
                        sweepAngle = 140f,
                        useCenter = false,
                        topLeft = Offset(centerX - baseRadius * 2.2f, centerY - baseRadius * 1.5f),
                        size = Size(baseRadius * 4.4f, baseRadius * 4.4f),
                        style = stroke
                    )
                    // 弧线2
                    drawArc(
                        color = outlineColor.copy(alpha = 0.6f),
                        startAngle = 210f,
                        sweepAngle = 120f,
                        useCenter = false,
                        topLeft = Offset(centerX - baseRadius * 1.4f, centerY - baseRadius * 0.7f),
                        size = Size(baseRadius * 2.8f, baseRadius * 2.8f),
                        style = stroke
                    )
                    // 中心圆点
                    drawCircle(
                        color = primaryColor,
                        radius = 4.dp.toPx(),
                        center = Offset(centerX, centerY + baseRadius * 0.9f)
                    )

                    // 断网斜线
                    drawLine(
                        color = Color(0xFFFF4D4F),
                        start = Offset(centerX - w * 0.28f, centerY - h * 0.28f),
                        end = Offset(centerX + w * 0.28f, centerY + h * 0.28f),
                        strokeWidth = 3.dp.toPx()
                    )
                }

                EmptyType.ERROR -> {
                    // 警告圆感叹号图案
                    drawCircle(
                        color = Color(0xFFFF4D4F).copy(alpha = 0.12f),
                        radius = w * 0.32f,
                        center = Offset(centerX, centerY)
                    )
                    drawCircle(
                        color = Color(0xFFFF4D4F),
                        radius = w * 0.32f,
                        center = Offset(centerX, centerY),
                        style = Stroke(width = 2.5.dp.toPx())
                    )
                    // 感叹号竖线
                    drawLine(
                        color = Color(0xFFFF4D4F),
                        start = Offset(centerX, centerY - h * 0.15f),
                        end = Offset(centerX, centerY + h * 0.05f),
                        strokeWidth = 3.5.dp.toPx()
                    )
                    // 感叹号点
                    drawCircle(
                        color = Color(0xFFFF4D4F),
                        radius = 2.5.dp.toPx(),
                        center = Offset(centerX, centerY + h * 0.15f)
                    )
                }

                EmptyType.CART -> {
                    // 购物车图案
                    val cartPath = Path().apply {
                        val startX = centerX - w * 0.28f
                        val startY = centerY - h * 0.18f
                        moveTo(startX, startY)
                        lineTo(startX + w * 0.1f, startY)
                        lineTo(startX + w * 0.2f, startY + h * 0.28f)
                        lineTo(centerX + w * 0.25f, startY + h * 0.28f)
                        lineTo(centerX + w * 0.3f, startY + h * 0.05f)
                        lineTo(startX + w * 0.15f, startY + h * 0.05f)
                    }
                    drawPath(
                        path = cartPath,
                        color = primaryColor,
                        style = Stroke(width = 2.5.dp.toPx())
                    )
                    // 两个轮子
                    drawCircle(
                        color = primaryColor,
                        radius = 3.dp.toPx(),
                        center = Offset(centerX - w * 0.04f, centerY + h * 0.18f)
                    )
                    drawCircle(
                        color = primaryColor,
                        radius = 3.dp.toPx(),
                        center = Offset(centerX + w * 0.2f, centerY + h * 0.18f)
                    )
                }
            }
        }
    }
}
