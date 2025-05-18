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

import eth.likespro.commons.numeric.HexUtils.toHex
import java.io.BufferedInputStream
import java.io.File
import java.security.MessageDigest

object FileHashUtils {
    /**
     * Computes the SHA-256 hash of the file and returns the result as a hexadecimal string.
     *
     * @param bufferSize The size of the buffer to be used while reading the file, in bytes. Default is 8 MB (8 * 1024 * 1024).
     * @return The SHA-256 hash of the file represented as a hexadecimal string prefixed with "0x".
     */
    fun File.sha256(bufferSize: Int = 8*1024*1024): String{
        val buffer = ByteArray(bufferSize)
        var count: Int
        val digest = MessageDigest.getInstance("SHA-256")
        val bis = BufferedInputStream(this.inputStream())
        while (bis.read(buffer).also { count = it } > 0) {
            digest.update(buffer, 0, count)
        }
        bis.close()

        val hash = digest.digest()
        return hash.toHex()
    }
}