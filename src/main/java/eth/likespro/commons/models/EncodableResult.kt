package eth.likespro.commons.models

import eth.likespro.commons.wrapping.WrappedException
import eth.likespro.commons.wrapping.WrappedException.Companion.wrap

class EncodableResult<T> (
    val value: T?,
    val failure: WrappedException?
) {
    val isSuccess: Boolean
        get() = value != null && failure == null
    val isFailure: Boolean
        get() = !isSuccess
    companion object {
        fun <T> success(data: T): EncodableResult<T> {
            return EncodableResult(data, null)
        }
        fun <T> failure(exception: WrappedException): EncodableResult<T> {
            return EncodableResult(null, exception)
        }
        fun <T> failure(exception: Throwable): EncodableResult<T> {
            return EncodableResult(null, exception.wrap())
        }
    }

    fun getOrNull(): T? {
        return value
    }
    fun onSuccess(action: (T) -> Unit): EncodableResult<T> {
        value?.let { action(it) }
        return this
    }
    fun onFailure(action: (WrappedException) -> Unit): EncodableResult<T> {
        failure?.let { action(it) }
        return this
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