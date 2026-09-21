package com.seanchen.widget.ui.loading

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.text.AppText
import com.seanchen.widget.ui.text.TextSize
import com.seanchen.widget.ui.theme.ShapeMedium
import com.seanchen.widget.ui.theme.SpaceHorizontalSmall
import com.seanchen.widget.ui.theme.SpacePaddingLarge
import com.seanchen.widget.ui.theme.SpaceVerticalSmall
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Canvas
import kotlin.math.roundToInt

enum class LoadingSize(
    val indicatorSize: Dp,
    val strokeWidth: Dp
) {
    SMALL(16.dp, 2.dp),
    MEDIUM(28.dp, 3.dp),
    LARGE(40.dp, 4.dp)
}

enum class LoadingOrientation {
    HORIZONTAL,
    VERTICAL
}

/**
 * 通用加载组件（行内/局部加载）
 *
 * @param modifier 修饰符
 * @param text 加载文案，为空则不显示文字
 * @param size 加载圈尺寸与粗细
 * @param orientation 文字与加载圈的排布方向
 * @param color 加载圈颜色，默认为主色
 * @param textColor 文字颜色
 * @param spacing 加载圈与文字之间的间距
 */
@Composable
fun AppLoading(
    modifier: Modifier = Modifier,
    text: String? = null,
    size: LoadingSize = LoadingSize.MEDIUM,
    orientation: LoadingOrientation = LoadingOrientation.VERTICAL,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    spacing: Dp = if (orientation == LoadingOrientation.VERTICAL) SpaceVerticalSmall else SpaceHorizontalSmall
) {
    val textStyle = when (size) {
        LoadingSize.SMALL -> MaterialTheme.typography.bodySmall
        LoadingSize.MEDIUM -> MaterialTheme.typography.bodyMedium
        LoadingSize.LARGE -> MaterialTheme.typography.bodyLarge
    }

    val content = @Composable {
        CircularProgressIndicator(
            modifier = Modifier.size(size.indicatorSize),
            color = color,
            strokeWidth = size.strokeWidth
        )
        if (!text.isNullOrEmpty()) {
            if (orientation == LoadingOrientation.VERTICAL) {
                Spacer(modifier = Modifier.height(spacing))
            } else {
                Spacer(modifier = Modifier.width(spacing))
            }
            Text(
                text = text,
                style = textStyle,
                color = textColor
            )
        }
    }

    if (orientation == LoadingOrientation.VERTICAL) {
        Column(
            modifier = modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    } else {
        Row(
            modifier = modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

/**
 * 模态全屏加载对话框，常用于等待异步接口响应并阻止用户交互
 *
 * @param visible 是否显示
 * @param text 提示文案，默认展示“加载中...”
 * @param onDismissRequest 关闭回调
 * @param cancellable 是否允许通过点击返回键或外部遮罩取消
 * @param modifier 弹窗修饰符
 */
@Composable
fun AppLoadingDialog(
    visible: Boolean,
    modifier: Modifier = Modifier,
    text: String? = stringResource(id = R.string.loading),
    onDismissRequest: () -> Unit = {},
    cancellable: Boolean = false
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = cancellable,
            dismissOnClickOutside = cancellable,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier.wrapContentSize(),
            shape = ShapeMedium,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            shadowElevation = 8.dp
        ) {
            Box(
                modifier = Modifier.padding(
                    horizontal = SpacePaddingLarge.times(1.5f),
                    vertical = SpacePaddingLarge
                ),
                contentAlignment = Alignment.Center
            ) {
                AppLoading(
                    text = text,
                    size = LoadingSize.MEDIUM,
                    orientation = LoadingOrientation.VERTICAL
                )
            }
        }
    }
}

@Composable
fun MiLoadingMobile(
    borderColor: Color = MaterialTheme.colorScheme.onSurface,
    dotColor: Color = borderColor,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(
        durationMillis = 1200,
        easing = LinearEasing
    )
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = animationSpec,
            repeatMode = RepeatMode.Restart
        ),
        label = "MiLoadingMobileAnimation"
    )

    Canvas(
        modifier = Modifier
            .size(28.dp)
            .border(2.dp, borderColor, CircleShape)
    ) {
        val circleRadius = size.minDimension / 2 - 8.dp.toPx()
        val dotRadius = 3.dp.toPx()
        val center = size.center
        val dotX = cos(Math.toRadians(angle.value.toDouble())) * circleRadius + center.x
        val dotY = sin(Math.toRadians(angle.value.toDouble())) * circleRadius + center.y

        drawCircle(dotColor, radius = dotRadius, center = Offset(dotX.toFloat(), dotY.toFloat()))
    }
}

@Composable
fun PageLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MiLoadingMobile(
            borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        SpaceVerticalSmall()
        AppText(
            text = "加载中",
            size = TextSize.BODY_MEDIUM
        )
    }
}

@Composable
fun WeLoadingMP(
    color: Color = MaterialTheme.colorScheme.outline,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(durationMillis = 1000)
){
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val currentIndex by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = animationSpec,
            repeatMode = RepeatMode.Restart
        ),
        label = "WeLoadingMPAnimation"
    )

    Canvas(modifier = Modifier.size(width = 44.dp, height = 20.dp)){
        val dotRadius = 4.dp.toPx()
        val spacing = (size.width - 2 * dotRadius) / 2
        repeat(3) { index ->
            val isActive = index == currentIndex.roundToInt()
            val dotColor = color.copy(alpha = if (isActive) 0.8f else 0.4f)
            val center = Offset(
                x = dotRadius + spacing * index,
                y = size.height / 2
            )

            drawCircle(
                color = dotColor,
                radius = dotRadius,
                center = center
            )


        }
    }
}