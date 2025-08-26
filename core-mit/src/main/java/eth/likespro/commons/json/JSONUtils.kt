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

package eth.likespro.commons.json

import org.json.JSONArray
import org.json.JSONObject

object JSONUtils {
    /**
     * Returns the value mapped by the specified key as a String, or null if the key is not present.
     *
     * @param key The key whose associated value is to be fetched.
     * @return The associated value as a String, or null if the key is not found.
     */
    fun JSONObject.getStringOrNull(key: String): String? { return if(this.has(key)) this.getString(key) else null }

    /**
     * Converts the current string to a [JSONObject] instance.
     * The string must be a valid JSON; otherwise, an [IllegalArgumentException] is thrown.
     *
     * @return A [JSONObject] representation of the string.
     * @throws IllegalArgumentException If the string is not a valid JSON.
     */
    fun String.toJSONObject(): JSONObject {
        return try {
            JSONObject(this)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON string: $this", e)
        }
    }

    /**
     * Converts the given JSON string representation to a [JSONArray] object.
     *
     * @return A [JSONArray] instance parsed from the string.
     * @throws IllegalArgumentException if the string is not a valid JSON array representation.
     */
    fun String.toJSONArray(): JSONArray {
        return try {
            JSONArray(this)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON array string: $this", e)
        }
    }
}