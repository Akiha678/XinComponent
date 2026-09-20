package com.seanchen.widget.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.divider.AppDivider
import com.seanchen.widget.ui.icon.ArrowRightIcon
import com.seanchen.widget.ui.text.AppText
import com.seanchen.widget.ui.text.TextSize
import com.seanchen.widget.ui.text.TextType
import com.seanchen.widget.ui.theme.SpaceHorizontalMedium
import com.seanchen.widget.ui.theme.SpaceHorizontalXSmall
import com.seanchen.widget.ui.theme.SpaceVerticalMedium

@Composable
fun AppListItem(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    leadingIconTint: Color = MaterialTheme.colorScheme.onSurface,
    leadingContent: @Composable (() -> Unit)? = null,
    description: String? = null,
    trailingText: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    showArrow: Boolean = true,
    showDivider: Boolean = true,
    verticalPadding: Dp = SpaceVerticalMedium,
    horizontalPadding: Dp = SpaceHorizontalMedium,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = verticalPadding, horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 前置内容：自定义内容或图标
            if (leadingContent != null) {
                leadingContent()
                SpaceHorizontalMedium()
            } else if (leadingIcon != null) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = title,
                    modifier = Modifier.size(20.dp),
                    tint = leadingIconTint
                )
                SpaceHorizontalMedium()
            }

            // 标题和描述
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // 标题
                AppText(
                    text = title,
                    size = TextSize.BODY_LARGE,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // 描述文本（如果有）
                if (!description.isNullOrEmpty()) {
                    AppText(
                        text = description,
                        type = TextType.SECONDARY,
                        size = TextSize.BODY_MEDIUM,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // 尾部内容
            if (trailingContent != null) {
                trailingContent()
            } else if (!trailingText.isNullOrEmpty()) {
                AppText(
                    text = trailingText,
                    type = TextType.SECONDARY,
                    size = TextSize.BODY_MEDIUM
                )
                SpaceHorizontalXSmall()
            }

            // 右箭头
            if (showArrow) {
                ArrowRightIcon(size = 16.dp)
            }
        }

        // 底部分隔线
        if (showDivider) {
            AppDivider()
        }
    }
}