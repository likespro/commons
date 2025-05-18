package eth.likespro.commons.models

import eth.likespro.commons.models.WrappedException.Companion.wrap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EncodableResultTest {
    @Test
    fun success_createsEncodableResultWithValueAndNoFailure() {
        val result = EncodableResult.success("Success")
        assertTrue(result.isSuccess)
        assertFalse(result.isFailure)
        assertEquals("Success", result.getOrNull())
        assertNull(result.failure)
    }

    @Test
    fun metadata_isSavedInResult() {
        val result = EncodableResult.success("Success", mapOf("hello" to "world"))
        assertEquals("world", result.metadata["hello"])
    }

    @Test
    fun failure_createsEncodableResultWithFailureAndNoValue() {
        val exception = Exception("Error").wrap()
        val result = EncodableResult.failure<String>(exception)
        assertTrue(result.isFailure)
        assertFalse(result.isSuccess)
        assertNull(result.getOrNull())
        assertEquals(exception, result.failure)
    }

    @Test
    fun getOrNull_returnsValue_whenResultIsSuccess() {
        val result = EncodableResult.success("Success")
        assertEquals("Success", result.getOrNull())
    }

    @Test
    fun getOrNull_returnsNull_whenResultIsFailure() {
        val result = EncodableResult.failure<String>(Exception("Error").wrap())
        assertNull(result.getOrNull())
    }

    @Test
    fun getOrDefault_returnsValue_whenResultIsSuccess() {
        val result = EncodableResult.success("Success")
        assertEquals("Success", result.getOrDefault("Default"))
    }

    @Test
    fun getOrDefault_returnsDefault_whenResultIsFailure() {
        val result = EncodableResult.failure<String>(Exception("Error").wrap())
        assertEquals("Default", result.getOrDefault("Default"))
    }

    @Test
    fun getOrElse_returnsValue_whenResultIsSuccess() {
        val result = EncodableResult.success("Success")
        assertEquals("Success", result.getOrElse { "Fallback" })
    }

    @Test
    fun getOrElse_returnsFallback_whenResultIsFailure() {
        val result = EncodableResult.failure<String>(Exception("Error").wrap())
        assertEquals("Fallback", result.getOrElse { "Fallback" })
    }

    @Test
    fun getOrThrow_returnsValue_whenResultIsSuccess() {
        val result = EncodableResult.success("Success")
        assertEquals("Success", result.getOrThrow())
    }

    @Test
    fun getOrThrow_throwsException_whenResultIsFailure() {
        val exception = NumberFormatException("Error").wrap()
        val result = EncodableResult.failure<String>(exception)
        val thrown = assertThrows<WrappedException.Exception> { result.getOrThrow() }
        assertEquals(exception, thrown.wrappedException)
    }

    @Test
    fun onSuccess_executesAction_whenResultIsSuccess() {
        val result = EncodableResult.success("Success")
        var actionExecuted = false
        result.onSuccess { actionExecuted = true }
        assertTrue(actionExecuted)
    }

    @Test
    fun onSuccess_doesNotExecuteAction_whenResultIsFailure() {
        val result = EncodableResult.failure<String>(Exception("Error").wrap())
        var actionExecuted = false
        result.onSuccess { actionExecuted = true }
        assertFalse(actionExecuted)
    }

    @Test
    fun onFailure_executesAction_whenResultIsFailure() {
        val exception = Exception("Error").wrap()
        val result = EncodableResult.failure<String>(exception)
        var actionExecuted = false
        result.onFailure { actionExecuted = true }
        assertTrue(actionExecuted)
    }

    @Test
    fun onFailure_doesNotExecuteAction_whenResultIsSuccess() {
        val result = EncodableResult.success("Success")
        var actionExecuted = false
        result.onFailure { actionExecuted = true }
        assertFalse(actionExecuted)
    }

    @Test
    fun equals_returnsTrue_whenObjectsAreIdentical() {
        val result = EncodableResult.success("Success", mapOf("key" to "value"))
        assertTrue(result == result)
    }

    @Test
    fun equals_returnsTrue_whenObjectsHaveSameValues() {
        val result1 = EncodableResult.success("Success", mapOf("key" to "value"))
        val result2 = EncodableResult.success("Success", mapOf("key" to "value"))
        assertTrue(result1 == result2)
    }

    @Test
    fun equals_returnsFalse_whenValuesAreDifferentAndSecondIsNull() {
        val result1 = EncodableResult.success("Success")
        assertFalse(result1.equals(null))
    }

    @Test
    fun equals_returnsFalse_whenValuesAreDifferent() {
        val result1 = EncodableResult.success("Success")
        val result2 = EncodableResult.success("Failure")
        assertFalse(result1 == result2)
    }

    @Test
    fun equals_returnsFalse_whenFailuresAreDifferent() {
        val result1 = EncodableResult.failure<String>(Exception("Error1").wrap())
        val result2 = EncodableResult.failure<String>(Exception("Error2").wrap())
        assertFalse(result1 == result2)
    }

    @Test
    fun equals_returnsFalse_whenMetadataIsDifferent() {
        val result1 = EncodableResult.success("Success", mapOf("key1" to "value1"))
        val result2 = EncodableResult.success("Success", mapOf("key2" to "value2"))
        assertFalse(result1 == result2)
    }

    @Test
    fun hashCode_isConsistent_whenCalledMultipleTimes() {
        val result = EncodableResult.success("Success", mapOf("key" to "value"))
        val hashCode1 = result.hashCode()
        val hashCode2 = result.hashCode()
        assertEquals(hashCode1, hashCode2)
    }

    @Test
    fun hashCode_isEqualForEqualObjects() {
        val result1 = EncodableResult.success("Success", mapOf("key" to "value"))
        val result2 = EncodableResult.success("Success", mapOf("key" to "value"))
        assertEquals(result1.hashCode(), result2.hashCode())
    }

    @Test
    fun hashCode_isDifferentForDifferentObjects() {
        val result1 = EncodableResult.success("Success")
        val result2 = EncodableResult.success("Failure")
        assertNotEquals(result1.hashCode(), result2.hashCode())
    }

    @Test
    fun hashCode_isDifferentForDifferentObjectsWithFailResult() {
        val result1 = EncodableResult.failure<String>(Exception("Success"))
        val result2 = EncodableResult.failure<String>(Exception("Failure"))
        assertNotEquals(result1.hashCode(), result2.hashCode())
    }
}