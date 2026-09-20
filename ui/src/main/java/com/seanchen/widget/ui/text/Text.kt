package com.seanchen.widget.ui.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seanchen.widget.ui.theme.ColorDanger
import com.seanchen.widget.ui.theme.ColorSuccess
import com.seanchen.widget.ui.theme.ColorWarning
import com.seanchen.widget.ui.theme.Primary
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

/**
 * 通用文本组件
 */
@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    type: TextType = TextType.PRIMARY,
    size: TextSize = TextSize.BODY_LARGE,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
    style: TextStyle? = null,
    selectable: Boolean = false
) {
    // 根据类型设置颜色
    val textColor = when {
        color != Color.Unspecified -> color
        type == TextType.PRIMARY -> Color.Unspecified // 使用默认Material颜色
        type == TextType.SECONDARY -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        type == TextType.TERTIARY -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        type == TextType.WHITE -> Color.White
        type == TextType.LINK -> Primary
        type == TextType.SUCCESS -> ColorSuccess
        type == TextType.WARNING -> ColorWarning
        type == TextType.ERROR -> ColorDanger
        else -> Color.Unspecified
    }

    // 根据大小设置文本样式
    val textStyle = style ?: when (size) {
        TextSize.DISPLAY_LARGE -> MaterialTheme.typography.displayLarge
        TextSize.DISPLAY_MEDIUM -> MaterialTheme.typography.displayMedium
        TextSize.TITLE_LARGE -> MaterialTheme.typography.titleLarge
        TextSize.TITLE_MEDIUM -> MaterialTheme.typography.titleMedium
        TextSize.BODY_LARGE -> MaterialTheme.typography.bodyLarge
        TextSize.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
        TextSize.BODY_SMALL -> MaterialTheme.typography.bodySmall
    }

    // 设置字体粗细（如果未指定，使用样式默认值）
    val finalFontWeight = fontWeight ?: textStyle.fontWeight

    // 创建修改后的样式
    val finalStyle = textStyle.copy(
        color = textColor,
        fontWeight = finalFontWeight,
        fontStyle = fontStyle ?: textStyle.fontStyle,
        fontFamily = fontFamily ?: textStyle.fontFamily,
        letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else textStyle.letterSpacing,
        textDecoration = textDecoration ?: textStyle.textDecoration,
        textAlign = textAlign ?: textStyle.textAlign,
        lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else textStyle.lineHeight
    )

    // 处理可点击状态
    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    // 处理可选择状态
    if (selectable) {
        SelectionContainer {
            Text(
                text = text,
                modifier = clickableModifier,
                style = finalStyle,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
            )
        }
    } else {
        Text(
            text = text,
            modifier = clickableModifier,
            style = finalStyle,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
        )
    }
}

/**
 * 通用文本组件 - AnnotatedString版本
 */
@Composable
fun AppText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    type: TextType = TextType.PRIMARY,
    size: TextSize = TextSize.BODY_LARGE,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
    style: TextStyle? = null,
    selectable: Boolean = false
) {
    // 根据类型设置颜色
    val textColor = when {
        color != Color.Unspecified -> color
        type == TextType.PRIMARY -> Color.Unspecified // 使用默认Material颜色
        type == TextType.SECONDARY -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        type == TextType.TERTIARY -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        type == TextType.WHITE -> Color.White
        type == TextType.LINK -> Primary
        type == TextType.SUCCESS -> ColorSuccess
        type == TextType.WARNING -> ColorWarning
        type == TextType.ERROR -> ColorDanger
        else -> Color.Unspecified
    }

    // 根据大小设置文本样式
    val textStyle = style ?: when (size) {
        TextSize.DISPLAY_LARGE -> MaterialTheme.typography.displayLarge
        TextSize.DISPLAY_MEDIUM -> MaterialTheme.typography.displayMedium
        TextSize.TITLE_LARGE -> MaterialTheme.typography.titleLarge
        TextSize.TITLE_MEDIUM -> MaterialTheme.typography.titleMedium
        TextSize.BODY_LARGE -> MaterialTheme.typography.bodyLarge
        TextSize.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium
        TextSize.BODY_SMALL -> MaterialTheme.typography.bodySmall
    }

    // 设置字体粗细（如果未指定，使用样式默认值）
    val finalFontWeight = fontWeight ?: textStyle.fontWeight

    // 创建修改后的样式
    val finalStyle = textStyle.copy(
        color = textColor,
        fontWeight = finalFontWeight,
        fontStyle = fontStyle ?: textStyle.fontStyle,
        fontFamily = fontFamily ?: textStyle.fontFamily,
        letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else textStyle.letterSpacing,
        textDecoration = textDecoration ?: textStyle.textDecoration,
        textAlign = textAlign ?: textStyle.textAlign,
        lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else textStyle.lineHeight
    )

    // 处理可点击状态
    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    // 处理可选择状态
    if (selectable) {
        SelectionContainer {
            Text(
                text = text,
                modifier = clickableModifier,
                style = finalStyle,
                overflow = overflow,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                onTextLayout = onTextLayout,
                color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
            )
        }
    } else {
        Text(
            text = text,
            modifier = clickableModifier,
            style = finalStyle,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            color = if (textColor == Color.Unspecified) Color.Unspecified else textColor
        )
    }
}
