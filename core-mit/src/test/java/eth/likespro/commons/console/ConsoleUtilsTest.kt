package eth.likespro.commons.console

import eth.likespro.commons.console.ConsoleUtils.progressIndicator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ConsoleUtilsTest {
    @Test
    fun progressIndicator_returnsCorrectProgressBar_whenProgressIsZero() {
        val result = progressIndicator(size = 10, progress = 0, total = 100)
        assertEquals(">.........", result)
    }

    @Test
    fun progressIndicator_returnsCorrectProgressBar_whenProgressIsHalf() {
        val result = progressIndicator(size = 10, progress = 50, total = 100)
        assertEquals("=====>....", result)
    }

    @Test
    fun progressIndicator_returnsCorrectProgressBar_whenProgressIsFull() {
        val result = progressIndicator(size = 10, progress = 100, total = 100)
        assertEquals("==========", result)
    }

    @Test
    fun progressIndicator_handlesEdgeCase_whenTotalIsZero() {
        assertThrows<ConsoleUtils.TotalCannotBeZero> { progressIndicator(size = 10, progress = 0, total = 0) }
    }

    @Test
    fun progressIndicator_handlesEdgeCase_whenProgressExceedsTotal() {
        val result = progressIndicator(size = 10, progress = 150, total = 100)
        assertEquals("==========", result)
    }



    @Test
    fun colorIndicator_returnsGreen_whenProgressIsLessThanHalfOfTotal() {
        val result = ConsoleUtils.colorIndicator(progress = 20, total = 100)
        assertEquals(ANSIColors.ANSI_GREEN, result)
    }

    @Test
    fun colorIndicator_returnsYellow_whenProgressIsBetweenHalfAndNinetyPercentOfTotal() {
        val result = ConsoleUtils.colorIndicator(progress = 70, total = 100)
        assertEquals(ANSIColors.ANSI_YELLOW, result)
    }

    @Test
    fun colorIndicator_returnsRed_whenProgressIsNinetyPercentOrMoreOfTotal() {
        val result = ConsoleUtils.colorIndicator(progress = 95, total = 100)
        assertEquals(ANSIColors.ANSI_RED, result)
    }

    @Test
    fun colorIndicator_handlesEdgeCase_whenProgressIsZero() {
        val result = ConsoleUtils.colorIndicator(progress = 0, total = 100)
        assertEquals(ANSIColors.ANSI_GREEN, result)
    }

    @Test
    fun colorIndicator_handlesEdgeCase_whenTotalIsZero() {
        assertThrows<ArithmeticException> { ConsoleUtils.colorIndicator(progress = 50, total = 0) }
    }

    @Test
    fun colorIndicator_handlesEdgeCase_whenProgressExceedsTotal() {
        val result = ConsoleUtils.colorIndicator(progress = 150, total = 100)
        assertEquals(ANSIColors.ANSI_RED, result)
    }
}