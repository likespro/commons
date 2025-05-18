package eth.likespro.commons.numeric

import eth.likespro.commons.numeric.NumericUtils.round
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NumericUtilsTest {
    @Test
    fun round_returnsSameValue_whenRoundingToZeroDecimalPlaces() {
        val result = 123.456.round(0)
        assertEquals(123.0, result)
    }

    @Test
    fun round_returnsRoundedValue_whenRoundingToTwoDecimalPlaces() {
        val result = 123.456.round(2)
        assertEquals(123.46, result)
    }

    @Test
    fun round_handlesEdgeCase_whenValueIsNegative() {
        val result = (-123.456).round(1)
        assertEquals(-123.5, result)
    }

    @Test
    fun round_handlesEdgeCase_whenValueIsZero() {
        val result = 0.0.round(3)
        assertEquals(0.0, result)
    }

    @Test
    fun round_throwsException_whenDecimalPlacesIsNegative() {
        assertThrows<IllegalArgumentException> { 123.456.round(-1) }
    }
}