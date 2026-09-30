package com.seanchen.widget.ui.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.seanchen.widget.ui.icon.ArrowLeftIcon
import com.seanchen.widget.ui.icon.MoreIcon

/**
 * 带有返回按钮与右侧菜单按钮的居中 TopAppBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuAppBar(
    title: Int? = null,
    titleText: String? = null,
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    showBackIcon: Boolean = true,
    showMenuIcon: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    ),
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (showBackIcon) {
                IconButton(onClick = onBackClick) {
                    ArrowLeftIcon()
                }
            }
        },
        title = {
            val finalTitle = titleText ?: title?.let { stringResource(it) } ?: ""
            if (finalTitle.isNotBlank()) {
                Text(
                    text = finalTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
            if (actions != null) {
                actions()
            } else if (showMenuIcon) {
                IconButton(onClick = onMenuClick) {
                    MoreIcon()
                }
            }
        },
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}
