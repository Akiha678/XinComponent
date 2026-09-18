package com.seanchen.widget.ui.badge

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class BadgeTest {

    @Test
    fun formatBadgeCount_belowMax_returnsExactNumber() {
        assertEquals("1", formatBadgeCount(1))
        assertEquals("10", formatBadgeCount(10))
        assertEquals("99", formatBadgeCount(99))
    }

    @Test
    fun formatBadgeCount_aboveMax_returnsTruncatedWithPlus() {
        assertEquals("99+", formatBadgeCount(100))
        assertEquals("99+", formatBadgeCount(1000))
        assertEquals("50+", formatBadgeCount(51, maxCount = 50))
    }

    @Test
    fun formatBadgeCount_zero_handledCorrectly() {
        assertNull(formatBadgeCount(0, showZero = false))
        assertEquals("0", formatBadgeCount(0, showZero = true))
    }

    @Test
    fun formatBadgeCount_negative_returnsNull() {
        assertNull(formatBadgeCount(-1))
        assertNull(formatBadgeCount(-10, showZero = true))
    }
}
