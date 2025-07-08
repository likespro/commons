package eth.likespro.commons.models.entity

import eth.likespro.commons.models.Entity
import eth.likespro.commons.models.Validatable
import eth.likespro.commons.models.Value
import eth.likespro.commons.models.value.Latitude
import eth.likespro.commons.models.value.Longitude
import eth.likespro.commons.models.value.PlaceId
import eth.likespro.commons.models.value.Timestamp
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CustomGeolocation(
    override val id: Id = Id(),
    var name: Name?,
    var latitude: Latitude?,
    var longitude: Longitude?,
    var placeId: PlaceId?,
    val createdAt: Timestamp = Timestamp.now(),
): Entity<CustomGeolocation.Id>, Validatable<CustomGeolocation> {

    class IsInvalidException(override val message: String) : RuntimeException()

    @Serializable
    data class Id(
        val value: String = UUID.randomUUID().toString()
    ) : Value, Validatable<Id> {
        class IsInvalidException(override val message: String) : RuntimeException()

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Id {
            if(value.length != 36)
                throw IsInvalidException("CustomGeolocation ID must be 36 characters long")

            return this
        }
    }

    @Serializable
    data class Name(
        val value: String
    ): Validatable<Name> {
        class IsInvalidException(override val message: String) : RuntimeException()

        init {
            throwIfInvalid()
        }

        override fun throwIfInvalid(): Name {
            if(value.length !in 1..48)
                throw IsInvalidException("Name must be between 1 and 48 characters long")

            return this
        }
    }

    init {
        throwIfInvalid()
    }

    override fun throwIfInvalid(): CustomGeolocation {
        if(name == null && latitude == null && longitude == null && placeId == null)
            throw IsInvalidException("At least one of the custom geolocation fields must be non-null")

        return this
    }
}