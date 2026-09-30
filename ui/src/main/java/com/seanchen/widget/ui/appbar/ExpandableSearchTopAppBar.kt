package com.seanchen.widget.ui.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.icon.ArrowLeftIcon

/**
 * 支持可展开/折叠搜索栏的通用 TopAppBar 组件
 *
 * @param modifier 修饰符
 * @param titleText 主标题文本（若自定义 title 插槽则优先使用 title）
 * @param subtitleText 副标题文本（例如联系人数量、在线状态等）
 * @param title 自定义标题区域插槽
 * @param navigationIcon 自定义导航图标插槽
 * @param onBackClick 点击返回回调（当 navigationIcon 为空且 showBackIcon 为 true 时有效）
 * @param showBackIcon 是否显示默认返回键
 * @param actions 右侧操作区域（如搜索切换按钮、排序按钮、更多菜单等）
 * @param isSearchActive 是否展开搜索栏
 * @param searchQuery 搜索输入内容
 * @param onSearchQueryChange 搜索输入变动回调
 * @param onSearch 键盘搜索点击回调
 * @param searchPlaceholder 搜索输入框占位提示文本
 * @param colors TopAppBar 颜色配置
 * @param searchBarContent 自定义展开的搜索栏内容，为空时使用默认 [AppSearchBar]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableSearchTopAppBar(
    modifier: Modifier = Modifier,
    titleText: String? = null,
    subtitleText: String? = null,
    title: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    onBackClick: () -> Unit = {},
    showBackIcon: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    isSearchActive: Boolean = false,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    searchPlaceholder: String = stringResource(R.string.search_hint),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface
    ),
    searchBarContent: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.containerColor)
    ) {
        TopAppBar(
            title = {
                if (title != null) {
                    title()
                } else {
                    Column {
                        if (!titleText.isNullOrBlank()) {
                            Text(
                                text = titleText,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (!subtitleText.isNullOrBlank()) {
                            Text(
                                text = subtitleText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                if (navigationIcon != null) {
                    navigationIcon()
                } else if (showBackIcon) {
                    IconButton(onClick = onBackClick) {
                        ArrowLeftIcon()
                    }
                }
            },
            actions = actions,
            colors = colors
        )

        AnimatedVisibility(
            visible = isSearchActive,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            if (searchBarContent != null) {
                searchBarContent()
            } else {
                AppSearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onSearch = onSearch,
                    placeholder = searchPlaceholder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
