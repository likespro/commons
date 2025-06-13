package eth.likespro.commons.models.value

import eth.likespro.commons.models.Value
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Timestamp(
    val value: Long
): Value {
    class TimestampIsInvalidException(override val message: String) : RuntimeException()

    init {
        if(value < 0)
            throw TimestampIsInvalidException("Timestamp must be greater than or equal to 0")
    }

    constructor(instant: Instant): this(instant.toEpochMilli())

    companion object {
        val NEVER = Timestamp(6307200000000000L)
        fun now(): Timestamp = Timestamp(Instant.now())
    }

    fun toInstant(): Instant = Instant.ofEpochMilli(value)

    operator fun compareTo(other: Timestamp): Int = value.compareTo(other.value)
    operator fun plus(other: Timestamp): Timestamp = Timestamp(value + other.value)
    operator fun minus(other: Timestamp): Timestamp = Timestamp(value - other.value)
    operator fun plus(millis: Long): Timestamp = Timestamp(value + millis)
    operator fun minus(millis: Long): Timestamp = Timestamp(value - millis)
}