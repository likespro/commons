/*
 * Copyright (c) 2025 likespro
 *
 * From https://github.com/likespro/commons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eth.likespro.commons.numeric

import java.security.SecureRandom
import java.util.Base64

object Base64Utils {
    /**
     * Encodes the contents of the ByteArray into a Base64 encoded string.
     *
     * @return a Base64 encoded representation of the ByteArray as a String.
     */
    fun ByteArray.toBase64(): String = Base64.getEncoder().encodeToString(this)

    /**
     * Decodes the Base64 encoded string into a byte array.
     *
     * This function uses the Base64 decoder to convert the string representation
     * into its corresponding byte array. The input string should be a valid
     * Base64 encoded string; otherwise, an IllegalArgumentException may be thrown
     * during decoding.
     *
     * @return A byte array representation of the decoded Base64 string.
     */
    fun String.base64ToByteArray(): ByteArray = Base64.getDecoder().decode(this)

    /**
     * Converts a Base64 encoded string to a URL-safe Base64 encoded string.
     *
     * This transformation replaces special characters '+' and '/' in the given Base64 string
     * with '-' and '_', respectively, to make the string safe for use in URLs.
     *
     * @return A URL-safe Base64 encoded string derived from the original Base64 encoded string.
     */
    fun String.base64ToUrlBase64(): String = Base64.getUrlEncoder().encodeToString(this.base64ToByteArray())

    /**
     * Converts a URL-safe Base64 encoded string to a standard Base64 encoded string.
     *
     * @return the standard Base64 encoded string.
     */
    fun String.urlBase64ToBase64(): String = Base64.getEncoder().encodeToString(Base64.getUrlDecoder().decode(this))

    /**
     * Generates a random Base64-encoded string of the specified length.
     *
     * @param length The desired length of the resulting Base64-encoded string.
     * @param useUrlEncoder If true, the URL-safe Base64 encoder will be used; defaults to false.
     * @return A random Base64-encoded string of the given length.
     */
    fun randomBase64(length: Int, useUrlEncoder: Boolean = false): String {
        val random = SecureRandom()
        val bytes = ByteArray(length*3/4)
        random.nextBytes(bytes)
        return (if(useUrlEncoder) Base64.getUrlEncoder() else Base64.getEncoder())
            .withoutPadding()
            .encodeToString(bytes)
    }
}