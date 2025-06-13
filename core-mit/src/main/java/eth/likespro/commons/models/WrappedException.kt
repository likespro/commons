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
data class WrappedException(
    val exceptionClass: Class<out Throwable>,
    var stackTrace: String,
    var message: String?,
    var cause: String?,
    var localizedMessage: String?
) {
    /**
     * Represents the configuration options for including various details
     * when handling or transforming exceptions in the WrappedException class.
     * Exception Class can't be excluded
     *
     * @property includeStackTrace Indicates whether the stack trace should be included.
     * @property includeMessage Indicates whether the exception message should be included.
     * @property includeCause Indicates whether the cause of the exception should be included.
     * @property includeLocalizedMessage Indicates whether the localized exception message should be included.
     */
    data class DetailsConfiguration(
        val includeStackTrace: Boolean = true,
        val includeMessage: Boolean = true,
        val includeCause: Boolean = true,
        val includeLocalizedMessage: Boolean = true
    ) {
        companion object {
            /**
             * A pre-defined configuration instance for including all available details of an exception.
             *
             * This configuration enables the inclusion of the following details:
             * - Exception class
             * - Stack trace
             * - Message
             * - Cause
             * - Localized message
             *
             * It can be used to configure how exception details should be extracted or presented,
             * ensuring that all available information about the exception is included.
             */
            val INCLUDE_ALL = DetailsConfiguration(
                includeStackTrace = true,
                includeMessage = true,
                includeCause = true,
                includeLocalizedMessage = true
            )

            /**
             * A predefined configuration for including only the exception class, message, and the cause
             * when handling or transforming exceptions using the `DetailsConfiguration` class.
             *
             * This configuration excludes the stack trace and the localized message.
             *
             * - `includeStackTrace`: false (stack trace is excluded)
             * - `includeMessage`: true (the exception message is included)
             * - `includeCause`: true (cause of the exception is included)
             * - `includeLocalizedMessage`: false (the localized exception message is excluded)
             */
            val INCLUDE_MESSAGE_AND_CAUSE = DetailsConfiguration(
                includeStackTrace = false,
                includeMessage = true,
                includeCause = true,
                includeLocalizedMessage = false
            )

            /**
             * A predefined configuration for including only the exception class and the cause
             * when handling or transforming exceptions using the `DetailsConfiguration` class.
             *
             * This configuration excludes the stack trace and the localized message.
             *
             * - `includeStackTrace`: false (stack trace is excluded)
             * - `includeMessage`: false (the exception message is excluded)
             * - `includeCause`: true (cause of the exception is included)
             * - `includeLocalizedMessage`: false (the localized exception message is excluded)
             */
            val INCLUDE_CAUSE = DetailsConfiguration(
                includeStackTrace = false,
                includeMessage = false,
                includeCause = true,
                includeLocalizedMessage = false
            )

            /**
             * A predefined configuration for including only the exception class
             * when handling or transforming exceptions using the `DetailsConfiguration` class.
             *
             * This configuration excludes the stack trace and the localized message.
             *
             * - `includeStackTrace`: false (stack trace is excluded)
             * - `includeMessage`: false (the exception message is excluded)
             * - `includeCause`: false (cause of the exception is excluded)
             * - `includeLocalizedMessage`: false (the localized exception message is excluded)
             */
            val INCLUDE_NOTHING = DetailsConfiguration(
                includeStackTrace = false,
                includeMessage = false,
                includeCause = false,
                includeLocalizedMessage = false
            )
        }
    }

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
    """.trimIndent()) {
        /**
         * Retrieves the wrapped exception housed within the current exception instance.
         *
         * @return The wrapped exception.
         */
        fun toWrappedException() = wrappedException
    }

    companion object {
        /**
         * Converts the exception to a wrapped exception if wrap is true.
         *
         * @param wrap A boolean indicating whether to wrap the exception or not.
         * @return The wrapped exception or the original exception.
         */
        fun Throwable.wrapMaybe(wrap: Boolean): Any {
            return if(wrap) this.wrap() else this
        }

        /**
         * Wraps the exception in a WrappedException.
         *
         * @return The wrapped exception.
         */
        fun Throwable.wrap() = if(this is WrappedException.Exception) this.toWrappedException() else this.toWrappedException()

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
     * Applies the specified configuration to the current exception, modifying the details
     * such as stack trace, message, cause, and localized message based on the configuration provided.
     *
     * @param configuration The configuration object that specifies which details should be included or excluded.
     * @return The current exception instance after applying the provided configuration.
     */
    fun applyDetailsConfiguration(configuration: DetailsConfiguration): WrappedException = this.apply {
        if(!configuration.includeStackTrace) stackTrace = ""
        if(!configuration.includeMessage) message = null
        if(!configuration.includeCause) cause = null
        if(!configuration.includeLocalizedMessage) localizedMessage = null
    }
}