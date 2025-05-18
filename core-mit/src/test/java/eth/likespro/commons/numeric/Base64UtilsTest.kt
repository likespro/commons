package eth.likespro.commons.numeric

import eth.likespro.commons.numeric.Base64Utils.base64ToByteArray
import eth.likespro.commons.numeric.Base64Utils.base64ToUrlBase64
import eth.likespro.commons.numeric.Base64Utils.toBase64
import eth.likespro.commons.numeric.Base64Utils.urlBase64ToBase64
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.Base64
import kotlin.text.Charsets.UTF_8

class Base64UtilsTest {

    @Test
    fun `toBase64 should correctly encode a byte array to Base64 string`() {
        // Given
        val input = "Hello, World!".toByteArray(UTF_8)
        val expected = Base64.getEncoder().encodeToString(input)

        // When
        val result = input.toBase64()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `toBase64 should correctly encode an empty byte array to Base64 string`() {
        // Given
        val input = byteArrayOf()
        val expected = Base64.getEncoder().encodeToString(input)

        // When
        val result = input.toBase64()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `base64ToByteArray should correctly decode a Base64 string back to byte array`() {
        // Given
        val input = Base64.getEncoder().encodeToString("TestInput".toByteArray(UTF_8))
        val expected = Base64.getDecoder().decode(input)

        // When
        val result = input.base64ToByteArray()

        // Then
        assertEquals(expected.contentToString(), result.contentToString())
    }

    @Test
    fun `base64ToByteArray should throw IllegalArgumentException for invalid Base64 string`() {
        // Given
        val input = "-!37&812-=)("

        // When & Then
        assertThrows(IllegalArgumentException::class.java) { input.base64ToByteArray() }
    }

    @Test
    fun `base64ToUrlBase64 should correctly convert Base64 string to URL-safe Base64 string`() {
        // Given
        val input = Base64.getEncoder().encodeToString("TestInput".toByteArray(UTF_8))
        val expected = Base64.getUrlEncoder().encodeToString(Base64.getDecoder().decode(input))

        // When
        val result = input.base64ToUrlBase64()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `urlBase64ToBase64 should correctly convert URL-safe Base64 string back to Base64 string`() {
        // Given
        val input = Base64.getUrlEncoder().encodeToString("TestInput".toByteArray(UTF_8))
        val expected = Base64.getEncoder().encodeToString(Base64.getUrlDecoder().decode(input))

        // When
        val result = input.urlBase64ToBase64()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `urlBase64ToBase64 should throw IllegalArgumentException for invalid URL-safe Base64 string`() {
        // Given
        val input = "Invalid-UrlBase64String*()"

        // When & Then
        assertThrows(IllegalArgumentException::class.java) { input.urlBase64ToBase64() }
    }
}