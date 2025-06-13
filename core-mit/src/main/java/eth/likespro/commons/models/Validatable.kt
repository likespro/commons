package eth.likespro.commons.models

import java.lang.Exception

interface Validatable<T> {
    /**
     * @return `true` if the object is valid, else `false`
     */
    fun validate(): Boolean = validatedOrNull() != null

    fun validatedOrNull(): T? = try {
        throwIfInvalid()
        this
    } catch (_: Exception) { null } as T?

    /**
     * Throws an Exception if the object is invalid
     */
    fun throwIfInvalid(): T
}