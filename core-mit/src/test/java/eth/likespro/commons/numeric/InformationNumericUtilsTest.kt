package eth.likespro.commons.numeric

import eth.likespro.commons.numeric.InformationNumericUtils.toReadableInformationSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InformationNumericUtilsTest {
    @Test
    fun int_toReadableInformationSize_returnsBytes_whenValueIsLessThanOneKilobyte() {
        val result = 512.toReadableInformationSize()
        assertEquals("512 B", result)
    }



    @Test
    fun toReadableInformationSize_returnsBytes_whenValueIsLessThanOneKilobyte() {
        val result = 512L.toReadableInformationSize()
        assertEquals("512 B", result)
    }

    @Test
    fun toReadableInformationSize_returnsKilobytes_whenValueIsBetweenOneKilobyteAndOneMegabyte() {
        val result = 2048L.toReadableInformationSize()
        assertEquals("2.0 KiB", result)
    }

    @Test
    fun toReadableInformationSize_returnsMegabytes_whenValueIsBetweenOneMegabyteAndOneGigabyte() {
        val result = 5_242_880L.toReadableInformationSize()
        assertEquals("5.0 MiB", result)
    }

    @Test
    fun toReadableInformationSize_returnsGigabytes_whenValueIsGreaterThanOrEqualToOneGigabyte() {
        val result = 10_737_418_240L.toReadableInformationSize()
        assertEquals("10.0 GiB", result)
    }

    @Test
    fun toReadableInformationSize_handlesEdgeCase_whenValueIsExactlyOneKilobyte() {
        val result = 1024L.toReadableInformationSize()
        assertEquals("1.0 KiB", result)
    }

    @Test
    fun toReadableInformationSize_handlesEdgeCase_whenValueIsExactlyOneMegabyte() {
        val result = 1_048_576L.toReadableInformationSize()
        assertEquals("1.0 MiB", result)
    }

    @Test
    fun toReadableInformationSize_handlesEdgeCase_whenValueIsExactlyOneGigabyte() {
        val result = 1_073_741_824L.toReadableInformationSize()
        assertEquals("1.0 GiB", result)
    }

    @Test
    fun toReadableInformationSize_roundsToSpecifiedDecimalPlaces() {
        val result = 1_073_741_824L.toReadableInformationSize(3)
        assertEquals("1.0 GiB", result)
    }
}