package com.seanchen.widget.ui.image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.theme.AvatarColors
import com.seanchen.widget.ui.theme.ColorOnline
import kotlin.math.abs

@Composable
fun Avatar(
    avatarUrl: String? = null,
    size: Dp = 36.dp,
    cornerShape: Shape = CircleShape,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
){
    val finalModifier = modifier
        .size(size)
        .clip(cornerShape)
        .let{ mod ->
            if (onClick != null) {
                mod.clickable { onClick.invoke()}
            } else {
                mod
            }
        }
    DefaultAvatar(
        size = size,
        modifier = finalModifier
    )
}

@Composable
fun DefaultAvatar(
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_my_fill),
            contentDescription = "默认头像",
            modifier = Modifier.size(size * 0.5f),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun SmallAvatar(
    avatarUrl: String? = null,
    size: Dp = 36.dp,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
){
    Avatar(
        avatarUrl = avatarUrl,
        size = size,
        onClick = onClick,
        modifier = modifier
    )
}

/**
 * 首字母文字头像组件，支持根据姓名哈希自动选取色彩，并支持在线状态指示圆点
 *
 * @param name 用户名称或昵称
 * @param modifier 修饰符
 * @param size 头像尺寸，默认 40.dp
 * @param backgroundColor 自定义背景颜色，如果不传则自动根据 name 取哈希色
 * @param textColor 文字颜色，默认白色
 * @param isOnline 是否在线（为 true 时在右下角展示在线小绿点）
 * @param shape 形状，默认圆形
 * @param onClick 点击事件
 */
@Composable
fun LetterAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    backgroundColor: Color? = null,
    textColor: Color = Color.White,
    isOnline: Boolean = false,
    shape: Shape = CircleShape,
    onClick: (() -> Unit)? = null
) {
    val initial = remember(name) {
        name.trim().take(1).uppercase().ifBlank { "?" }
    }
    val bgColor = remember(name, backgroundColor) {
        backgroundColor ?: run {
            val hash = abs(name.hashCode())
            AvatarColors[hash % AvatarColors.size]
        }
    }

    val clickableModifier = if (onClick != null) {
        modifier.clickable(onClick = onClick)
    } else {
        modifier
    }

    Box(
        modifier = clickableModifier.size(size)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(shape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            val fontSize = (size.value * 0.42f).sp
            Text(
                text = initial,
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        if (isOnline) {
            val indicatorSize = (size * 0.28f).coerceAtLeast(8.dp)
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .align(Alignment.BottomEnd)
                    .background(ColorOnline, CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
            )
        }
    }
}
