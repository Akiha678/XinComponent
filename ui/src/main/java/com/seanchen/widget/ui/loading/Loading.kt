package com.seanchen.widget.ui.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.theme.ShapeMedium
import com.seanchen.widget.ui.theme.SpaceHorizontalSmall
import com.seanchen.widget.ui.theme.SpacePaddingLarge
import com.seanchen.widget.ui.theme.SpaceVerticalSmall

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