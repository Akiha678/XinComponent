package com.seanchen.widget.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.theme.RightArrowGray

@Composable
fun CommonIcon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = LocalContentColor.current
){
    val finalModifier = if (size != null){
        modifier.then(Modifier.size(size))
    } else {
        modifier
    }
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = finalModifier,
        tint = tint
    )
}

@Composable
fun CommonIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = LocalContentColor.current
) {
    val finalModifier = if (size != null) {
        modifier.then(Modifier.size(size))
    } else {
        modifier
    }

    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = finalModifier,
        tint = tint
    )
}

@Composable
fun CommonIcon(
    resId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = LocalContentColor.current
) {
    CommonIcon(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier,
        size = size,
        tint = tint
    )
}



/**
 * 左箭头图标
 */
@Composable
fun ArrowLeftIcon(
    modifier: Modifier = Modifier,
    size: Dp? = 28.dp,
    tint: Color = LocalContentColor.current
) {
    CommonIcon(
        resId = R.drawable.ic_left,
        modifier = modifier,
        size = size,
        tint = tint
    )
}

/**
 * 右箭头图标
 */
@Composable
fun ArrowRightIcon(
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = RightArrowGray
) {
    CommonIcon(
        resId = R.drawable.ic_right,
        modifier = modifier,
        size = size,
        tint = tint
    )
}
/**
 * 更多图标（三点）
 */
@Composable
fun MoreIcon(
    modifier: Modifier = Modifier,
    size: Dp? = 24.dp,
    tint: Color = LocalContentColor.current
) {
    CommonIcon(
        resId = R.drawable.ic_more,
        contentDescription = "More",
        modifier = modifier,
        size = size,
        tint = tint
    )
}
