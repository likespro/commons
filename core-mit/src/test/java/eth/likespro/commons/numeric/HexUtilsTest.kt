package eth.likespro.commons.numeric

import eth.likespro.commons.numeric.HexUtils.hexToByteArray
import eth.likespro.commons.numeric.HexUtils.toHex
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HexUtilsTest {
    @Test
    fun hexToByteArray_convertsValidHexStringWithoutPrefix() {
        val result = "1a2b3c".hexToByteArray()
        assertArrayEquals(byteArrayOf(0x1a, 0x2b, 0x3c), result)
    }

    @Test
    fun hexToByteArray_convertsValidHexStringWithPrefix() {
        val result = "0x1a2b3c".hexToByteArray()
        assertArrayEquals(byteArrayOf(0x1a, 0x2b, 0x3c), result)
    }

    @Test
    fun hexToByteArray_handlesEdgeCase_whenHexStringIsEmpty() {
        val result = "".hexToByteArray()
        assertArrayEquals(byteArrayOf(), result)
    }

    @Test
    fun hexToByteArray_throwsException_whenHexStringHasOddLength() {
        assertThrows<NumberFormatException> { "1a2b3".hexToByteArray() }
    }

    @Test
    fun hexToByteArray_throwsException_whenHexStringContainsInvalidCharactersOnOddIndices() {
        assertThrows<NumberFormatException> { "g12h3i".hexToByteArray() }
    }

    @Test
    fun hexToByteArray_throwsException_whenHexStringContainsInvalidCharactersOnEvenIndices() {
        assertThrows<NumberFormatException> { "1g2h3i".hexToByteArray() }
    }



    @Test
    fun toHexString_convertsEmptyByteArrayToHex() {
        val result = byteArrayOf().toHex()
        assertEquals("0x", result)
    }

    @Test
    fun toHexString_convertsSingleByteToHex() {
        val result = byteArrayOf(0x0F).toHex()
        assertEquals("0x0f", result)
    }

    @Test
    fun toHexString_convertsMultipleBytesToHex() {
        val result = byteArrayOf(0x1A, 0x2B, 0x3C).toHex()
        assertEquals("0x1a2b3c", result)
    }

    @Test
    fun toHex_handlesEdgeCase_whenByteArrayContainsZero() {
        val result = byteArrayOf(0x00, 0x1A).toHex()
        assertEquals("0x001a", result)
    }

    @Test
    fun toHex_handlesEdgeCase_whenByteArrayContainsNegativeValues() {
        val result = byteArrayOf(-1, -128, 127).toHex()
        assertEquals("0xff807f", result)
    }
}