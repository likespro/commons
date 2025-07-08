package eth.likespro.commons.models.value

import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable

@Serializable
data class PlaceId(
    val value: String
): Value, Validatable<PlaceId> {
    class IsInvalidException(override val message: String) : RuntimeException()

    init {
        throwIfInvalid()
    }

    override fun throwIfInvalid(): PlaceId {
        if (value.length !in 1..128)
            throw IsInvalidException("Place ID must be between 1 and 128 characters long")

        return this
    }
}