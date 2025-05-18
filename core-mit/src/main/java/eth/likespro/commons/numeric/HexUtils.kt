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

object HexUtils {
    /**
     * Converts a hexadecimal string to a byte array. The String can be either with leading `0x` or without.
     *
     * @return The byte array representation of the hexadecimal string.
     */
    fun String.hexToByteArray(): ByteArray {
        val rawNumber = this.replace("0x", "")
        if(rawNumber.length % 2 != 0) throw NumberFormatException("Invalid HEX string length: $this")
        val result = ByteArray(rawNumber.length / 2)
        for (i in rawNumber.indices step 2) {
            val byteString = rawNumber.substring(i, i + 2)
            val firstDigit = Character.digit(byteString[0], 16).let { if(it == -1) throw NumberFormatException("Invalid HEX digit ${byteString[0]} found.") else it }
            val secondDigit = Character.digit(byteString[1], 16).let { if(it == -1) throw NumberFormatException("Invalid HEX digit ${byteString[1]} found.") else it }
            result[i / 2] = ((firstDigit shl 4) + secondDigit).toByte()
        }
        return result
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @return The hexadecimal string representation of the byte array (with leading `0x`).
     */
    fun ByteArray.toHex(): String {
        val hexString = StringBuilder()
        for (byte in this) {
            val hex = Integer.toHexString(byte.toInt() and 0xFF)
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return "0x$hexString"
    }
}