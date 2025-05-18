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

import eth.likespro.commons.numeric.Base64Utils.toBase64
import eth.likespro.commons.numeric.HexUtils.toHex
import java.security.MessageDigest

object StringHashUtils {
    /**
     * Computes the SHA-256 hash of a string and returns the result as a byte array.
     *
     * @return The SHA-256 hash of the string as a byte array.
     */
    fun String.sha256(): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
    }

    /**
     * Computes the SHA-256 hash of the string and returns the result as a hexadecimal string.
     *
     * @return The SHA-256 hash of the string represented as a lowercase hexadecimal string.
     */
    fun String.sha256Hex(): String {
        return this.sha256().toHex()
    }

    /**
     * Computes the SHA-256 hash of the string and encodes the result as a Base64 URL-safe string without padding.
     *
     * @return The SHA-256 hash of the string represented as a Base64 URL-safe string.
     */
    fun String.sha256Base64(): String {
        return this.sha256().toBase64()
    }
}