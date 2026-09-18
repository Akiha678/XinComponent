package com.seanchen.widget.ui.rate

import org.junit.Assert.assertEquals
import org.junit.Test

class RateTest {

    @Test
    fun calculateStarState_fullScore() {
        assertEquals(StarState.FULL, calculateStarState(1, 5f))
        assertEquals(StarState.FULL, calculateStarState(5, 5f))
    }

    @Test
    fun calculateStarState_halfStar() {
        assertEquals(StarState.FULL, calculateStarState(3, 3.5f))
        assertEquals(StarState.HALF, calculateStarState(4, 3.5f))
        assertEquals(StarState.EMPTY, calculateStarState(5, 3.5f))
    }

    @Test
    fun calculateStarState_allowHalfFalse() {
        // 当不允许半星时，只要达到 0.5 也按全星计算
        assertEquals(StarState.FULL, calculateStarState(4, 3.5f, allowHalf = false))
        assertEquals(StarState.EMPTY, calculateStarState(4, 3.2f, allowHalf = false))
    }

    @Test
    fun calculateStarState_zeroScore() {
        assertEquals(StarState.EMPTY, calculateStarState(1, 0f))
        assertEquals(StarState.EMPTY, calculateStarState(5, 0f))
    }
}
