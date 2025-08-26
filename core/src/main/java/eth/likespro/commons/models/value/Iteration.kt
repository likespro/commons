package eth.likespro.commons.models.value

import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable

@Serializable
data class Iteration(
    val value: Long,
): Value, Validatable<Iteration> {
    class IsInvalidException(override val message: String) : RuntimeException()

    init {
        throwIfInvalid()
    }

    override fun throwIfInvalid(): Iteration {
        if(value < 0)
            throw IsInvalidException("Iteration must be not less than 0")

        return this
    }
}