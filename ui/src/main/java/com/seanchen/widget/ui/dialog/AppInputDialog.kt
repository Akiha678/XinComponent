package com.seanchen.widget.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R

/**
 * 通用输入对话框组件
 *
 * @param visible 是否可见
 * @param title 对话框标题
 * @param onConfirm 确认回调，参数为去除首尾空格后的文本
 * @param onDismiss 取消/关闭回调
 * @param modifier 修饰符
 * @param initialValue 初始输入值
 * @param placeholder 占位提示文案
 * @param maxLength 最大允许输入字符数
 * @param showCharacterCount 是否显示字数统计
 * @param clearable 是否支持一键清空
 * @param singleLine 是否单行输入
 * @param confirmText 确认按钮文案
 * @param cancelText 取消按钮文案
 * @param confirmLoading 提交中状态，为 true 时显示加载菊花并禁用操作
 * @param validate 文本合法性校验，默认要求非空
 */
@Composable
fun AppInputDialog(
    visible: Boolean,
    title: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialValue: String = "",
    placeholder: String = "",
    maxLength: Int = Int.MAX_VALUE,
    showCharacterCount: Boolean = true,
    clearable: Boolean = true,
    singleLine: Boolean = true,
    confirmText: String = stringResource(R.string.confirm),
    cancelText: String = stringResource(R.string.cancel),
    confirmLoading: Boolean = false,
    validate: (String) -> Boolean = { it.trim().isNotEmpty() }
) {
    if (!visible) return

    var text by remember(initialValue) { mutableStateOf(initialValue) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val trimmed = text.trim()
    val isValid = (maxLength == Int.MAX_VALUE || trimmed.length <= maxLength) && validate(trimmed)
    val canSubmit = isValid && !confirmLoading

    AlertDialog(
        onDismissRequest = {
            if (!confirmLoading) onDismiss()
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { newText ->
                        if (maxLength == Int.MAX_VALUE || newText.length <= maxLength) {
                            text = newText
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = singleLine,
                    enabled = !confirmLoading,
                    placeholder = if (placeholder.isNotBlank()) {
                        { Text(text = placeholder) }
                    } else null,
                    supportingText = if (showCharacterCount && maxLength != Int.MAX_VALUE) {
                        { Text(text = "${text.length}/$maxLength") }
                    } else null,
                    trailingIcon = if (clearable && text.isNotEmpty() && !confirmLoading) {
                        {
                            IconButton(onClick = { text = "" }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = stringResource(R.string.delete),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    } else null,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (canSubmit) {
                                onConfirm(trimmed)
                            }
                        }
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(trimmed) },
                enabled = canSubmit
            ) {
                if (confirmLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = confirmText)
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !confirmLoading
            ) {
                Text(text = cancelText)
            }
        },
        modifier = modifier
    )
}
