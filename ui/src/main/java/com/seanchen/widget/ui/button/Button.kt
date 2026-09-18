package com.seanchen.widget.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.theme.ColorDanger
import com.seanchen.widget.ui.theme.ColorPurple
import com.seanchen.widget.ui.theme.ColorSuccess
import com.seanchen.widget.ui.theme.ColorWarning
import com.seanchen.widget.ui.theme.Primary
import com.seanchen.widget.ui.theme.TextWhite
import com.seanchen.widget.ui.theme.ShapeSmall

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    style: ButtonStyle = ButtonStyle.FILLED,
    size: ButtonSize = ButtonSize.MEDIUM,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    fullWidth: Boolean = true,
) {
    AppButtonCore(
        text = text,
        onClick = onClick,
        modifier = if (fullWidth) modifier.fillMaxWidth() else modifier,
        type = type,
        style = style,
        height = size.height,
        shape = shape,
        enabled = enabled,
        loading = loading,
        textStyle = MaterialTheme.typography.titleMedium,
        contentPadding = ButtonDefaults.ContentPadding,
    )
}

/** Compact button whose width is determined by its content. */
@Composable
fun AppButtonFixed(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    style: ButtonStyle = ButtonStyle.FILLED,
    size: ButtonSize = ButtonSize.SMALL,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
) {
    AppButtonCore(
        text = text,
        onClick = onClick,
        modifier = modifier,
        type = type,
        style = style,
        height = size.height,
        shape = shape,
        enabled = enabled,
        loading = loading,
        textStyle = MaterialTheme.typography.titleMedium,
        contentPadding = contentPadding,
    )
}

/** Compatibility wrapper for an outlined compact button. */
@Composable
fun AppButtonBordered(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    size: ButtonSize = ButtonSize.SMALL,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    color: Color? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    height: Dp? = null,
) {
    AppButtonCore(
        text = text,
        onClick = onClick,
        modifier = modifier,
        type = type,
        style = ButtonStyle.OUTLINED,
        height = height ?: size.height,
        shape = shape,
        enabled = enabled,
        loading = loading,
        textStyle = textStyle,
        contentPadding = contentPadding,
        customColor = color,
    )
}

/** Button variant for layouts that require an explicit width or height. */
@Composable
fun AppButtonCustomSize(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.DEFAULT,
    style: ButtonStyle = ButtonStyle.FILLED,
    width: Dp? = null,
    height: Dp? = null,
    shape: ButtonShape = ButtonShape.ROUND,
    enabled: Boolean = true,
    loading: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    var sizedModifier = modifier
    if (width != null) sizedModifier = sizedModifier.width(width)

    AppButtonCore(
        text = text,
        onClick = onClick,
        modifier = sizedModifier,
        type = type,
        style = style,
        height = height ?: ButtonSize.SMALL.height,
        shape = shape,
        enabled = enabled,
        loading = loading,
        textStyle = textStyle,
        contentPadding = ButtonDefaults.ContentPadding,
    )
}

@Composable
private fun AppButtonCore(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    type: ButtonType,
    style: ButtonStyle,
    height: Dp,
    shape: ButtonShape,
    enabled: Boolean,
    loading: Boolean,
    textStyle: TextStyle,
    contentPadding: PaddingValues,
    customColor: Color? = null,
) {
    val buttonColor = customColor ?: type.color
    val contentColor = if (type == ButtonType.WARNING) Color.Black else TextWhite
    val displayedContentColor = contentColor.copy(
        alpha = if (enabled || loading) 1f else DisabledContentAlpha,
    )
    val resolvedShape = when (shape) {
        ButtonShape.SQUARE -> ShapeSmall
        ButtonShape.ROUND -> RoundedCornerShape(percent = 50)
    }
    val interactionEnabled = enabled && !loading
    val sizedModifier = modifier.height(height)

    when (style) {
        ButtonStyle.FILLED -> Button(
            onClick = onClick,
            enabled = interactionEnabled,
            modifier = sizedModifier,
            shape = resolvedShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = contentColor,
                disabledContainerColor = buttonColor.copy(alpha = DisabledAlpha),
                disabledContentColor = contentColor.copy(alpha = DisabledContentAlpha),
            ),
            contentPadding = contentPadding,
        ) {
            ButtonContent(text, loading, textStyle, displayedContentColor)
        }

        ButtonStyle.OUTLINED -> OutlinedButton(
            onClick = onClick,
            enabled = interactionEnabled,
            modifier = sizedModifier,
            shape = resolvedShape,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = buttonColor,
                disabledContentColor = buttonColor.copy(alpha = DisabledAlpha),
            ),
            border = BorderStroke(
                width = 1.dp,
                color = buttonColor.copy(alpha = if (interactionEnabled) 1f else DisabledAlpha),
            ),
            contentPadding = contentPadding,
        ) {
            ButtonContent(
                text = text,
                loading = loading,
                textStyle = textStyle,
                color = buttonColor.copy(alpha = if (enabled || loading) 1f else DisabledAlpha),
            )
        }

        ButtonStyle.GRADIENT -> {
            val gradient = Brush.horizontalGradient(
                listOf(
                    lerp(buttonColor, Color.White, 0.15f),
                    lerp(buttonColor, Color.Black, 0.15f),
                )
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = sizedModifier
                    .clip(resolvedShape)
                    .background(gradient, alpha = if (interactionEnabled) 1f else DisabledAlpha)
                    .clickable(
                        enabled = interactionEnabled,
                        role = Role.Button,
                        onClick = onClick,
                    )
                    .padding(contentPadding),
            ) {
                ButtonContent(text, loading, textStyle, displayedContentColor)
            }
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    loading: Boolean,
    textStyle: TextStyle,
    color: Color,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = text,
            style = textStyle,
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(if (loading) 0f else 1f),
        )
        if (loading) {
            CircularProgressIndicator(
                color = color,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
            )
        }
    }
}

private val ButtonSize.height: Dp
    get() = when (this) {
        ButtonSize.MEDIUM -> 48.dp
        ButtonSize.SMALL -> 40.dp
        ButtonSize.MINI -> 34.dp
    }

private val ButtonType.color: Color
    @Composable get() = when (this) {
        ButtonType.DEFAULT, ButtonType.LINK -> Primary
        ButtonType.SUCCESS -> ColorSuccess
        ButtonType.WARNING -> ColorWarning
        ButtonType.DANGER -> ColorDanger
        ButtonType.PURPLE -> ColorPurple
    }

private const val DisabledAlpha = 0.48f
private const val DisabledContentAlpha = 0.72f
