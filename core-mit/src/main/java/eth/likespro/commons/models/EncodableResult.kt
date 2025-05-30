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

import eth.likespro.commons.models.WrappedException.Companion.wrap

class EncodableResult<T> private constructor (
    private val value: T?,
    val failure: WrappedException?,
    val metadata: Map<String, Any?> = emptyMap()
) {
    /**
     * Indicates whether the result is a success.
     *
     * @return true if the result is a success, false otherwise.
     */
    val isSuccess: Boolean
        get() = failure == null

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
         * @param metadata Optional metadata to be associated with the result.
         * @return A new EncodableResult instance representing a success.
         */
        fun <T> success(data: T, metadata: Map<String, Any?> = emptyMap()): EncodableResult<T> {
            return EncodableResult(data, null, metadata)
        }

        /**
         * Creates a new EncodableResult instance representing a failure.
         *
         * @param exception The exception to be wrapped in the result.
         * @param metadata Optional metadata to be associated with the result.
         * @return A new EncodableResult instance representing a failure.
         */
        fun <T> failure(exception: WrappedException, metadata: Map<String, Any?> = emptyMap()): EncodableResult<T> {
            return EncodableResult(null, exception, metadata)
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
        @Suppress("UNCHECKED_CAST")
        if(failure == null) action(value as T)
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
        @Suppress("UNCHECKED_CAST")
        return if(failure == null) value as T else defaultValue
    }

    /**
     * Returns the value if it is present, otherwise invokes the specified function [onFailure] and returns its result.
     *
     * @param onFailure a function that takes a WrappedException and returns a value of type T
     * @return the value if present, otherwise the result of invoking [onFailure]
     */
    fun getOrElse(onFailure: (WrappedException) -> T): T {
        @Suppress("UNCHECKED_CAST")
        return if(failure == null) value as T else onFailure(failure)
    }

    /**
     * Throws the exception if the result is a failure and returns the result in the opposite situation.
     *
     * @throws WrappedException if the result is a failure.
     * @return the value if present.
     */
    fun getOrThrow(): T {
        @Suppress("UNCHECKED_CAST")
        return if(failure == null) value as T else throw failure.toException()
    }

    /**
     * Erases the stack trace of the associated failure, if any, within the current result.
     *
     * @return The current EncodableResult instance with the stack trace of the failure erased.
     */
    fun eraseStackTrace(): EncodableResult<T> = this.apply { failure?.eraseStackTrace() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncodableResult<*>

        if (value != other.value) return false
        if (failure != other.failure) return false
        if (metadata != other.metadata) return false

        return true
    }
    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + (failure?.hashCode() ?: 0)
        result = 31 * result + metadata.hashCode()
        return result
    }
}