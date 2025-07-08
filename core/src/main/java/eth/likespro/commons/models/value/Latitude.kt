package eth.likespro.commons.models.value

import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable

@Serializable
data class Latitude(
    val value: Double
): Value, Validatable<Latitude> {
    class IsInvalidException(override val message: String) : RuntimeException()

    init {
        throwIfInvalid()
    }

    override fun throwIfInvalid(): Latitude {
        if (value < -90.0 || value > 90.0)
            throw IsInvalidException("Latitude must be between -90 and 90 degrees")

        return this
    }
}