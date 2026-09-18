package com.seanchen.widget.ui.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.theme.ColorDanger
import com.seanchen.widget.ui.theme.ShapeSmall
import com.seanchen.widget.ui.theme.SpaceVerticalXSmall

/**
 * XinComponent 通用输入框组件
 *
 * @param value 当前输入文本
 * @param onValueChange 文本变动回调
 * @param modifier 修饰符
 * @param placeholder 占位提示文案
 * @param label 输入框标签文案
 * @param leadingIcon 前置图标/组件
 * @param trailingIcon 自定义后置图标/组件
 * @param clearable 是否支持一键清空（输入内容不为空时展示清除按钮）
 * @param isPassword 是否为密码模式（内置密码可见/不可见切换开关）
 * @param errorMessage 错误提示文案，不为空时自动置为错误状态并高亮红框
 * @param enabled 是否可用
 * @param readOnly 是否只读
 * @param singleLine 是否单行输入
 * @param maxLines 最大行数
 * @param shape 边框形状
 * @param keyboardOptions 软键盘选项
 * @param keyboardActions 软键盘动作
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    clearable: Boolean = false,
    isPassword: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    shape: Shape = ShapeSmall,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var passwordVisible by remember { mutableStateOf(!isPassword) }
    val isError = !errorMessage.isNullOrEmpty()

    val visualTransformation = if (isPassword && !passwordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    val resolvedTrailingIcon: (@Composable () -> Unit)? = when {
        trailingIcon != null -> trailingIcon
        isPassword -> {
            {
                val iconRes = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = if (passwordVisible) "隐藏密码" else "显示密码",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        clearable && value.isNotEmpty() && enabled && !readOnly -> {
            {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "清空输入",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
        else -> null
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            maxLines = maxLines,
            shape = shape,
            isError = isError,
            label = label?.let { { Text(it) } },
            placeholder = if (placeholder.isNotEmpty()) {
                {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            } else null,
            leadingIcon = leadingIcon,
            trailingIcon = resolvedTrailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = ColorDanger,
                errorLabelColor = ColorDanger,
                errorSupportingTextColor = ColorDanger
            )
        )

        if (isError) {
            Spacer(modifier = Modifier.height(SpaceVerticalXSmall))
            Text(
                text = errorMessage.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = ColorDanger,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}
