package eth.likespro.commons.models

import eth.likespro.commons.models.WrappedException.Companion.toWrappedException
import eth.likespro.commons.models.WrappedException.Companion.wrap
import eth.likespro.commons.models.WrappedException.Companion.wrapMaybe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WrappedExceptionTest {
    @Test
    fun wrap_convertsThrowableToWrappedException() {
        val exception = IllegalArgumentException("Test exception")
        val wrapped = exception.wrap()
        assertEquals(IllegalArgumentException::class.java, wrapped.exceptionClass)
        assertEquals("Test exception", wrapped.message)
    }

    @Test
    fun wrap_convertsThrowableToWrappedExceptionViaWrapMaybe() {
        val exception = IllegalArgumentException("Test exception")
        val wrapped = exception.wrapMaybe(true) as WrappedException
        assertEquals(IllegalArgumentException::class.java, wrapped.exceptionClass)
        assertEquals("Test exception", wrapped.message)
    }

    @Test
    fun wrap_notConvertsThrowableToWrappedExceptionViaWrapMaybe() {
        val exception = IllegalArgumentException("Test exception")
        val wrapped = exception.wrapMaybe(false)
        assertEquals(exception, wrapped)
    }

    @Test
    fun toWrappedException_createsWrappedExceptionWithCorrectDetails() {
        val exception = NullPointerException("Null pointer")
        val wrapped = exception.toWrappedException()
        assertEquals(NullPointerException::class.java, wrapped.exceptionClass)
        assertEquals("Null pointer", wrapped.message)
        assertEquals(exception.stackTraceToString(), wrapped.stackTrace)
        assertNull(wrapped.cause)
        assertEquals(exception.localizedMessage, wrapped.localizedMessage)
    }

    @Test
    fun toWrappedException_includesCauseMessageIfPresent() {
        val cause = IllegalStateException("Cause exception")
        val exception = RuntimeException("Parent exception", cause)
        val wrapped = exception.toWrappedException()
        assertEquals("Cause exception", wrapped.cause)
    }
}