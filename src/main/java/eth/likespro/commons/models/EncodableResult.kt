package eth.likespro.commons.models

import eth.likespro.commons.wrapping.WrappedException
import eth.likespro.commons.wrapping.WrappedException.Companion.wrap

class EncodableResult<T> (
    val value: T?,
    val failure: WrappedException?
) {
    /**
     * Indicates whether the result is a success.
     *
     * @return true if the result is a success, false otherwise.
     */
    val isSuccess: Boolean
        get() = value != null && failure == null

    /**
     * Indicates whether the result is a failure.
     *
     * @return true if the result is a failure, false otherwise.
     */
    val isFailure: Boolean
        get() = !isSuccess

    companion object {
        /**
         * Creates a new EncodableResult instance representing a success.
         *
         * @param data The data to be wrapped in the result.
         * @return A new EncodableResult instance representing a success.
         */
        fun <T> success(data: T): EncodableResult<T> {
            return EncodableResult(data, null)
        }

        /**
         * Creates a new EncodableResult instance representing a failure.
         *
         * @param exception The exception to be wrapped in the result.
         * @return A new EncodableResult instance representing a failure.
         */
        fun <T> failure(exception: WrappedException): EncodableResult<T> {
            return EncodableResult(null, exception)
        }

        /**
         * Creates a new EncodableResult instance representing a failure.
         *
         * @param exception The exception to be wrapped in the result.
         * @return A new EncodableResult instance representing a failure.
         */
        fun <T> failure(exception: Throwable): EncodableResult<T> {
            return EncodableResult(null, exception.wrap())
        }
    }

    /**
     * Executes the given action if the result is a success.
     *
     * @param action The action to be executed if the result is a success.
     * @return The current EncodableResult instance.
     */
    fun onSuccess(action: (T) -> Unit): EncodableResult<T> {
        value?.let { action(it) }
        return this
    }

    /**
     * Executes the given action if the result is a failure.
     *
     * @param action The action to be executed if the result is a failure.
     * @return The current EncodableResult instance.
     */
    fun onFailure(action: (WrappedException) -> Unit): EncodableResult<T> {
        failure?.let { action(it) }
        return this
    }

    /**
     * Returns the value if it is present, otherwise returns null.
     *
     * @return the value if present, otherwise null
     */
    fun getOrNull(): T? {
        return value
    }

    /**
     * Returns the value if it is present, otherwise returns the specified [defaultValue].
     *
     * @param defaultValue the value to return if the result is a failure
     * @return the value if present, otherwise [defaultValue]
     */
    fun getOrDefault(defaultValue: T): T {
        return value ?: defaultValue
    }

    /**
     * Returns the value if it is present, otherwise invokes the specified function [onFailure] and returns its result.
     *
     * @param onFailure a function that takes a WrappedException and returns a value of type T
     * @return the value if present, otherwise the result of invoking [onFailure]
     */
    fun getOrElse(onFailure: (WrappedException) -> T): T {
        return value ?: onFailure(failure!!)
    }

    /**
     * Throws the exception if the result is a failure and returns the result in the opposite situation.
     *
     * @throws WrappedException if the result is a failure.
     * @return the value if present.
     */
    fun getOrThrow(): T {
        return value ?: throw failure!!.toException()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncodableResult<*>

        if (value != other.value) return false
        if (failure != other.failure) return false

        return true
    }
    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + (failure?.hashCode() ?: 0)
        return result
    }
}