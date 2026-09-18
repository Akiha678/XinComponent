package com.seanchen.widget.ui.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.icon.CommonIcon
import com.seanchen.widget.ui.theme.BgGreenLight
import com.seanchen.widget.ui.theme.BgPurpleLight
import com.seanchen.widget.ui.theme.BgRedLight
import com.seanchen.widget.ui.theme.BgYellowLight
import com.seanchen.widget.ui.theme.ColorDanger
import com.seanchen.widget.ui.theme.ColorSuccess
import com.seanchen.widget.ui.theme.ColorWarning
import com.seanchen.widget.ui.theme.Primary
import com.seanchen.widget.ui.theme.ShapeSmall
import com.seanchen.widget.ui.theme.SpacePaddingSmall
import com.seanchen.widget.ui.theme.SpacePaddingXSmall
import com.seanchen.widget.ui.theme.TextWhite

enum class TagType {
    DEFAULT,
    PRIMARY,
    WARNING,
    DANGER,
    SUCCESS
}

enum class TagStyle {
    FILLED,
    LIGHT,
    OUTLINED
}

enum class TagSize {
    SMALL,
    MEDIUM,
    LARGE
}

@Composable
private fun getTagColors(type: TagType, style: TagStyle): Pair<Color, Color> {
    return when (style) {
        TagStyle.FILLED -> {
            when (type) {
                TagType.DEFAULT -> Pair(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.colorScheme.surfaceVariant
                )

                TagType.PRIMARY -> Pair(TextWhite, Primary)
                TagType.WARNING -> Pair(TextWhite, ColorWarning)
                TagType.DANGER -> Pair(TextWhite, ColorDanger)
                TagType.SUCCESS -> Pair(TextWhite, ColorSuccess)
            }
        }

        TagStyle.LIGHT -> {
            when (type) {
                TagType.DEFAULT -> Pair(
                    MaterialTheme.colorScheme.onSurface,
                    MaterialTheme.colorScheme.surfaceVariant
                )

                TagType.PRIMARY -> Pair(Primary, BgPurpleLight)
                TagType.WARNING -> Pair(ColorWarning, BgYellowLight)
                TagType.DANGER -> Pair(ColorDanger, BgRedLight)
                TagType.SUCCESS -> Pair(ColorSuccess, BgGreenLight)
            }
        }

        TagStyle.OUTLINED -> {
            when (type) {
                TagType.DEFAULT -> Pair(
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    Color.Transparent
                )

                TagType.PRIMARY -> Pair(Primary, Color.Transparent)
                TagType.WARNING -> Pair(ColorWarning, Color.Transparent)
                TagType.DANGER -> Pair(ColorDanger, Color.Transparent)
                TagType.SUCCESS -> Pair(ColorSuccess, Color.Transparent)
            }
        }
    }
}

@Composable
fun Tag(
    text: String,
    type: TagType = TagType.DEFAULT,
    style: TagStyle = TagStyle.FILLED,
    size: TagSize = TagSize.MEDIUM,
    shape: Shape = ShapeSmall,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
) {
    val (textColor, backgroundColor) = getTagColors(type, style)

    val padding = when (size) {
        TagSize.SMALL -> SpacePaddingXSmall
        TagSize.MEDIUM -> SpacePaddingSmall
        TagSize.LARGE -> SpacePaddingSmall.times(1.5f)
    }

    var tagModifier = if (style == TagStyle.OUTLINED) {
        Modifier
            .clip(shape)
            .border(1.dp, textColor, shape)
            .background(backgroundColor)
            .padding(horizontal = padding.times(1.5f), vertical = padding)
    } else {
        Modifier
            .clip(shape)
            .background(backgroundColor)
            .padding(horizontal = padding.times(1.5f), vertical = padding)
    }

    tagModifier = modifier.then(tagModifier)

    Box(
        contentAlignment = Alignment.Center,
        modifier = tagModifier
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}


@Composable
fun TagClosable(
    text: String,
    onClose: () -> Unit,
    type: TagType = TagType.DEFAULT,
    style: TagStyle = TagStyle.FILLED,
    size: TagSize = TagSize.MEDIUM,
    shape: Shape = ShapeSmall,
    closeIcon: Int = R.drawable.ic_close,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
) {
    val (textColor, backgroundColor) = getTagColors(type, style)

    val padding = when (size) {
        TagSize.SMALL -> SpacePaddingXSmall
        TagSize.MEDIUM -> SpacePaddingSmall
        TagSize.LARGE -> SpacePaddingSmall.times(1.5f)
    }

    val iconSize = when (size) {
        TagSize.SMALL -> 12.dp
        TagSize.MEDIUM -> 16.dp
        TagSize.LARGE -> 18.dp
    }

    var tagModifier = if (style == TagStyle.OUTLINED) {
        Modifier
            .clip(shape)
            .border(1.dp, textColor, shape)
            .background(backgroundColor)
            .padding(
                start = padding.times(1.5f),
                top = padding,
                bottom = padding,
                end = padding
            )
    } else {
        Modifier
            .clip(shape)
            .background(backgroundColor)
            .padding(
                start = padding.times(1.5f),
                top = padding,
                bottom = padding,
                end = padding
            )
    }
    tagModifier = modifier.then(tagModifier)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = tagModifier
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )

        Spacer(modifier = Modifier.width(4.dp))

        CommonIcon(
            resId = closeIcon,
            contentDescription = stringResource(id = R.string.modal_close),
            tint = textColor,
            size = iconSize,
            modifier = Modifier.clickable{ onClose() }
        )
    }
}