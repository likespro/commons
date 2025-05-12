package eth.likespro.commons.wrapping

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
    val stackTrace: String,
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
        fun Throwable.wrap(): WrappedException {
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
}