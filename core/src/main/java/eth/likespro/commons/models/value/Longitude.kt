package eth.likespro.commons.models.value

import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable

@Serializable
data class Longitude(
    val value: Double
): Value, Validatable<Longitude> {
    class IsInvalidException(override val message: String) : RuntimeException()

    init {
        throwIfInvalid()
    }

    override fun throwIfInvalid(): Longitude {
        if (value < -180.0 || value > 180.0)
            throw IsInvalidException("Longitude must be between -180 and 180 degrees")

        return this
    }
}