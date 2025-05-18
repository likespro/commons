/*
 * Copyright 2025 likespro.
 *
 * From https://github.com/likespro/commons
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eth.likespro.commons.security.hashing

import eth.likespro.commons.security.hashing.StringHashUtils.sha256
import eth.likespro.commons.security.hashing.StringHashUtils.sha256Base64
import eth.likespro.commons.security.hashing.StringHashUtils.sha256Hex
import eth.likespro.commons.numeric.HexUtils.hexToByteArray
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringHashUtilsTest {

    /**
     * Tests the `sha256` method of the `StringHashUtils` class.
     * It ensures that the method correctly computes the SHA-256 hash as a byte array.
     */
    @Test
    fun `sha256 should return correct hash for empty string`() {
        // Given
        val input = ""

        // When
        val hash = input.sha256()

        // Then
        val expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855".hexToByteArray()
        assertArrayEquals(expectedHash, hash)
    }

    /**
     * Tests the `sha256Hex` method of the `StringHashUtils` class.
     * It ensures that the method computes the SHA-256 hash and encodes it as a hexadecimal string.
     */
    @Test
    fun `sha256Hex should return correct hexadecimal hash for string`() {
        // Given
        val input = "hello"

        // When
        val hexHash = input.sha256Hex()

        // Then
        val expectedHexHash = "0x2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"
        assertEquals(expectedHexHash, hexHash)
    }

    /**
     * Tests the `sha256Base64` method of the `StringHashUtils` class.
     * It ensures that the method computes the SHA-256 hash and encodes it as a base64 string.
     */
    @Test
    fun `sha256Base64 should return correct base64 hash for string`() {
        // Given
        val input = "world"

        // When
        val base64Hash = input.sha256Base64()

        // Then
        val expectedBase64Hash = "SG6kYiTRu0+2gPNPfJrZao8k7Ii+c+qOWmxlJg6cuKc="
        assertEquals(expectedBase64Hash, base64Hash)
    }
}