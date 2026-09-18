package com.seanchen.widget.ui.rate

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seanchen.widget.ui.R
import com.seanchen.widget.ui.theme.ColorWarning

enum class StarState {
    FULL,
    HALF,
    EMPTY
}

/**
 * 根据分值计算指定序号星星（从 1 开始）的显示状态。
 *
 * @param starIndex 星星序号（1..maxScore）
 * @param score 当前分值
 * @param allowHalf 是否允许半星
 */
fun calculateStarState(
    starIndex: Int,
    score: Float,
    allowHalf: Boolean = true
): StarState {
    val diff = score - (starIndex - 1)
    return when {
        diff >= 1f -> StarState.FULL
        diff >= 0.5f && allowHalf -> StarState.HALF
        diff >= 0.5f && !allowHalf -> StarState.FULL
        diff > 0f && allowHalf -> StarState.HALF
        else -> StarState.EMPTY
    }
}

/**
 * 星级评分组件，常用于评价、商品评分展示与交互选择。
 *
 * @param score 当前评分值（例如 3.5f）
 * @param modifier 修饰符
 * @param maxScore 最大星数，默认为 5
 * @param onScoreChange 评分发生改变时的回调。为 null 时表示只读模式
 * @param allowHalf 是否支持半星评分，默认为 true
 * @param starSize 星星图标尺寸
 * @param space 星星之间的间距
 * @param activeColor 点亮时的颜色，默认使用温暖金黄色
 * @param inactiveColor 未点亮时的颜色
 */
@Composable
fun AppRate(
    score: Float,
    modifier: Modifier = Modifier,
    maxScore: Int = 5,
    onScoreChange: ((Float) -> Unit)? = null,
    allowHalf: Boolean = true,
    starSize: Dp = 24.dp,
    space: Dp = 4.dp,
    activeColor: Color = ColorWarning,
    inactiveColor: Color = Color(0xFFD9D9D9)
) {
    val isReadOnly = onScoreChange == null

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 1..maxScore) {
            val starState = calculateStarState(index, score, allowHalf)

            val (iconRes, iconTint) = when (starState) {
                StarState.FULL -> Pair(R.drawable.ic_star_filled, activeColor)
                StarState.HALF -> Pair(R.drawable.ic_star_half, activeColor)
                StarState.EMPTY -> Pair(R.drawable.ic_star_outline, inactiveColor)
            }

            val itemModifier = Modifier
                .size(starSize)
                .then(
                    if (!isReadOnly) {
                        Modifier.pointerInput(allowHalf) {
                            detectTapGestures { offset ->
                                val selectedScore = if (allowHalf) {
                                    if (offset.x < size.width / 2f) {
                                        index - 0.5f
                                    } else {
                                        index.toFloat()
                                    }
                                } else {
                                    index.toFloat()
                                }
                                onScoreChange(selectedScore)
                            }
                        }
                    } else {
                        Modifier
                    }
                )

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "评分星星 $index",
                modifier = itemModifier,
                tint = iconTint
            )
        }
    }
}
