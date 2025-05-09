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
    val exceptionClass: Class<out Exception>,
    val stackTrace: String,
    val message: String?,
    val cause: String?,
    val localizedMessage: String?
) {
    constructor(e: Exception) : this(
        e::class.java,
        e.stackTraceToString(),
        e.message,
        e.cause?.message,
        e.localizedMessage
    )

    companion object {
        /**
         * Converts the exception to a wrapped exception if wrap is true.
         *
         * @param wrap A boolean indicating whether to wrap the exception or not.
         * @return The wrapped exception or the original exception.
         */
        fun Exception.wrapMaybe(wrap: Boolean): Any {
            return if(wrap) WrappedException(this) else this
        }

        /**
         * Wraps the exception in a WrappedException.
         *
         * @return The wrapped exception.
         */
        fun Exception.wrap(): WrappedException {
            return WrappedException(this)
        }
    }
}