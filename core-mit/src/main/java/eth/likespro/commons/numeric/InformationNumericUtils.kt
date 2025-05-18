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

import eth.likespro.commons.numeric.NumericUtils.round

object InformationNumericUtils {
    /**
     * Converts an Int value to a human-readable information size string (for example, "1.5 GiB", "512 MiB").
     *
     * @param maxPlaces The maximum number of decimal places to include in the output.
     * @return A string representing the information size in a human-readable format.
     */
    fun Int.toReadableInformationSize(maxPlaces: Int = 1): String = (this.toLong()).toReadableInformationSize(maxPlaces)

    /**
     * Converts a Long value to a human-readable information size string (for example, "1.5 GiB", "512 MiB").
     *
     * @param maxPlaces The maximum number of decimal places to include in the output.
     * @return A string representing the information size in a human-readable format.
     */
    fun Long.toReadableInformationSize(maxPlaces: Int = 1): String {
        return if(this >= 1024*1024*1024) (this/1024.0/1024.0/1024.0).round(maxPlaces).toString()+" GiB"
        else if(this >= 1024*1024) (this/1024.0/1024.0).round(maxPlaces).toString()+" MiB"
        else if(this >= 1024) (this/1024.0).round(1).toString()+" KiB"
        else "$this B"
    }
}