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

package eth.likespro.commons.models

/**
 * A wrapper class for exceptions to make them serializable.
 *
 * @param exceptionClass The class of the exception.
 * @param stackTrace The stack trace of the exception as a string.
 * @param message The message of the exception.
 * @param cause The cause of the exception as a string.
 * @param localizedMessage The localized message of the exception.
 */
class WrappedException(
    val exceptionClass: Class<out Throwable>,
    var stackTrace: String,
    val message: String?,
    val cause: String?,
    val localizedMessage: String?
) {
    constructor(e: Throwable) : this(
        e::class.java,
        e.stackTraceToString(),
        e.message,
        e.cause?.message,
        e.localizedMessage
    )

    /**
     * Wraps WrappedException again in real Exception
     *
     * @param wrappedException The WrappedException to be wrapped.
     *
     * @return Real Exception containing WrappedException.
     */
    class Exception(val wrappedException: WrappedException) : RuntimeException("""This Exception is wrapping for WrappedException.
        Original Exception: ${wrappedException.exceptionClass.name}
        Message: ${wrappedException.message}
        LocalizedMessage: ${wrappedException.localizedMessage}
        Cause: ${wrappedException.cause}
        StackTrace:
        ${wrappedException.stackTrace}
        ---------------------------
    """.trimIndent())

    companion object {
        /**
         * Converts the exception to a wrapped exception if wrap is true.
         *
         * @param wrap A boolean indicating whether to wrap the exception or not.
         * @return The wrapped exception or the original exception.
         */
        fun Throwable.wrapMaybe(wrap: Boolean): Any {
            return if(wrap) WrappedException(this) else this
        }

        /**
         * Wraps the exception in a WrappedException.
         *
         * @return The wrapped exception.
         */
        fun Throwable.wrap() = this.toWrappedException()

        /**
         * Converts the exception to a WrappedException.
         *
         * @return The wrapped exception.
         */
        fun Throwable.toWrappedException(): WrappedException {
            return WrappedException(this)
        }
    }

    /**
     * Converts the wrapped exception to a WrappedExceptionException.
     *
     * @return The WrappedExceptionException.
     */
    fun toException(): Exception {
        return Exception(this)
    }

    /**
     * Clears the stack trace string of the current WrappedException instance.
     *
     * @return The current WrappedException instance with the stack trace erased.
     */
    fun eraseStackTrace(): WrappedException = this.apply { stackTrace = "" }
}