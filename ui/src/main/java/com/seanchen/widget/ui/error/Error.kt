package com.seanchen.widget.ui.error

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seanchen.widget.ui.empty.EmptyNetwork
import com.seanchen.widget.ui.loading.PageLoading

sealed class BaseNetWorkUiState<out T> {
    /**
     * 加载中状态
     */
    data object Loading : BaseNetWorkUiState<Nothing>()

    /**
     * 成功状态
     */
    data class Success<T>(var data: T) : BaseNetWorkUiState<T>()

    /**
     * 错误状态
     */
    data class Error(val message: String? = null, val exception: Throwable? = null) : BaseNetWorkUiState<Nothing>()
}

@Composable
fun <T> BaseNetworkView(
    uiState: BaseNetWorkUiState<T>,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    onRetry: () -> Unit = {},
    chatLoading: @Composable (() -> Unit)? = null,
    chatError: @Composable (() -> Unit)? = null,
    content: @Composable (data: T) -> Unit
) {
    Box(
        modifier = modifier.padding(padding)
    ) {
        AnimatedContent(
            targetState = uiState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "NetworkStateAnimation"
        ) { state ->
            when (state) {
                is BaseNetWorkUiState.Loading -> {
                    if (chatLoading != null) {
                        chatLoading()
                    } else {
                        PageLoading()
                    }
                }

                is BaseNetWorkUiState.Error -> {
                    if (chatError != null) {
                        chatError()
                    } else {
                        EmptyNetwork()
                    }
                }
                is BaseNetWorkUiState.Success -> content(state.data)
            }
        }
    }
}