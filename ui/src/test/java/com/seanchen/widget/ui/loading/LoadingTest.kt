package com.seanchen.widget.ui.loading

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoadingTest {

    @Test
    fun loadingSize_incrementsProperly() {
        assertTrue(LoadingSize.SMALL.indicatorSize < LoadingSize.MEDIUM.indicatorSize)
        assertTrue(LoadingSize.MEDIUM.indicatorSize < LoadingSize.LARGE.indicatorSize)

        assertTrue(LoadingSize.SMALL.strokeWidth < LoadingSize.MEDIUM.strokeWidth)
        assertTrue(LoadingSize.MEDIUM.strokeWidth < LoadingSize.LARGE.strokeWidth)
    }

    @Test
    fun loadingSize_expectedDefaultValues() {
        assertEquals(16f, LoadingSize.SMALL.indicatorSize.value)
        assertEquals(28f, LoadingSize.MEDIUM.indicatorSize.value)
        assertEquals(40f, LoadingSize.LARGE.indicatorSize.value)
    }
}
