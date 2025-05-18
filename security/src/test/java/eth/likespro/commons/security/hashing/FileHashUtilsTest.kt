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

import eth.likespro.commons.security.hashing.FileHashUtils.sha256
import eth.likespro.commons.security.hashing.StringHashUtils.sha256Hex
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.IOException
import java.security.MessageDigest

class FileHashUtilsTest {

    @Test
    fun `test sha256 with empty file`() {
        val tempFile = File.createTempFile("emptyFile", ".tmp")
        tempFile.writeText("")
        try {
            val expectedHash = MessageDigest.getInstance("SHA-256")
                .digest(byteArrayOf())
                .joinToString(separator = "") { "%02x".format(it) }
            val result = tempFile.sha256()
            assertEquals("0x$expectedHash", result)
        } finally {
            tempFile.delete()
        }
    }

    @Test
    fun `test sha256 with small file`() {
        val tempFile = File.createTempFile("smallFile", ".tmp")
        val content = "Hello, World!"
        tempFile.writeText(content)
        try {
            val expectedHash = MessageDigest.getInstance("SHA-256")
                .digest(content.toByteArray())
                .joinToString(separator = "") { "%02x".format(it) }
            val result = tempFile.sha256()
            assertEquals("0x$expectedHash", result)
        } finally {
            tempFile.delete()
        }
    }

    @Test
    fun `test sha256 with large file`() {
        val tempFile = File.createTempFile("largeFile", ".tmp")
        val content = "A".repeat(10_000_000)
        tempFile.writeText(content)
        try {
            val expectedHash = MessageDigest.getInstance("SHA-256")
                .digest(content.toByteArray())
                .joinToString(separator = "") { "%02x".format(it) }
            val result = tempFile.sha256()
            assertEquals("0x$expectedHash", result)
        } finally {
            tempFile.delete()
        }
    }

    @Test
    fun `test sha256 with non-existent file`() {
        val tempFile = File("nonExistentFile.tmp")
        val exception = assertThrows<IOException> {
            tempFile.sha256()
        }
        assertTrue(exception.message!!.startsWith("nonExistentFile.tmp"))
    }

    @Test
    fun `test sha256 with custom buffer size`() {
        val tempFile = File.createTempFile("customBufferSizeFile", ".tmp")
        val content = "Kotlin is awesome!"
        tempFile.writeText(content)
        try {
            val expectedHash = content.sha256Hex()
            val result = tempFile.sha256(bufferSize = 4 * 1024)
            assertEquals(expectedHash, result)
        } finally {
            tempFile.delete()
        }
    }
}