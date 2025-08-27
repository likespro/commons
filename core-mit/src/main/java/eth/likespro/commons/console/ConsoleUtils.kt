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

package eth.likespro.commons.console

import kotlin.math.min

object ConsoleUtils {
    class TotalCannotBeZero : RuntimeException("Total amount of work to be done cannot be zero")

    /**
     * Prints a progress indicator in the console.
     *
     * @param size The total size of the progress bar.
     * @param progress The current progress.
     * @param total The total amount of work to be done.
     * @param completeSymbol The symbol used to represent completed progress.
     * @param processingSymbol The symbol used to represent the current processing position.
     * @param unprocessedSymbol The symbol used to represent unprocessed progress.
     * @return A string representing the progress indicator.
     */
    fun progressIndicator(size: Long, progress: Long, total: Long, completeSymbol: Char = '=', processingSymbol: Char = '>', unprocessedSymbol: Char = '.'): String{
        if(total == 0L) throw TotalCannotBeZero()
        var res = ""
        val complete = min(progress, total) *size/total
        (0..<complete).forEach { _ -> res+=completeSymbol }
        if(complete < size) { res+=processingSymbol }
        (0..<(size-complete-1)).forEach { _ -> res+=unprocessedSymbol }
        return res
    }

    /**
     * Returns a color code based on the progress percentage.
     *
     * @param progress The current progress.
     * @param total The total amount of work to be done.
     * @return A string representing the color code for the progress indicator.
     */
    fun colorIndicator(progress: Long, total: Long): String{
        val percentage = progress * 100 / total
        return if(percentage < 50) ANSIColors.ANSI_GREEN
        else if(percentage < 90) ANSIColors.ANSI_YELLOW
        else ANSIColors.ANSI_RED
    }

    /**
     * Methods for clearing the console.
     */
    enum class ClearConsoleMethod {
        /**
         * Clears the console using the Windows method.
         */
        WINDOWS,
        /**
         * Clears the console using the Unix method.
         */
        UNIX,
        /**
         * Clears the console by printing many newlines ('\n').
         */
        NEWLINES
    }
    /**
     * Clears the console screen.
     *
     * @param method The method to use for clearing the console.
     */
    fun clearConsole(method: ClearConsoleMethod = ClearConsoleMethod.NEWLINES){
        when(method){
            ClearConsoleMethod.WINDOWS -> ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
            ClearConsoleMethod.UNIX, ClearConsoleMethod.NEWLINES -> (0..100).forEach { _ -> println() } // TODO implement Unix fast method
        }
    }
}