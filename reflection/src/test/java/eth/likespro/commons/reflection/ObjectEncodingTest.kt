/*
 * Copyright 2025 likespro.
 *
 * From https://github.com/likespro/commons
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eth.likespro.commons.reflection

import com.google.gson.JsonParseException
import eth.likespro.commons.reflection.ObjectEncoding.decodeObject
import eth.likespro.commons.reflection.ObjectEncoding.encodeObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.math.BigInteger

class ObjectEncodingTest {
    @Test
    fun encodeObject_returnsJsonStringRepresentation() {
        val obj = mapOf("key" to "value")
        val json = obj.encodeObject()
        assertEquals("""{"key":"value"}""", json)
    }

    @Test
    fun decodeObject_decodesValidJsonStringToObject() {
        val json = """{"key":"value"}"""
        val obj: Map<String, String> = json.decodeObject() // json.decodeObject(Map::class.java.getParametrizedType(String::class.java, String::class.java)) as Map<String, String>
        assertEquals(mapOf("key" to "value"), obj)
    }

    @Test
    fun decodeObject_handlesValidPrimitiveTypeBoolean() {
        val json = """{"key":true}"""
        assertTrue(json.decodeObject<Map<String, Boolean>>()["key"]!!)
    }

    @Test
    fun decodeObject_handlesValidPrimitiveTypeByte() {
        val json = """{"key":123}"""
        val byteValue: Byte = json.decodeObject<Map<String, Byte>>()["key"]!!
        assertEquals(123.toByte(), byteValue)
    }

    @Test
    fun decodeObject_handlesValidPrimitiveTypeShort() {
        val json = """{"key":123}"""
        val shortValue: Short = json.decodeObject<Map<String, Short>>()["key"]!!
        assertEquals(123.toShort(), shortValue)
    }

    @Test
    fun decodeObject_handlesValidPrimitiveTypeInt() {
        val json = """{"key":123}"""
        val intValue: Int = json.decodeObject<Map<String, Int>>()["key"]!!
        assertEquals(123, intValue)
    }

    @Test
    fun decodeObject_handlesValidPrimitiveTypeLong() {
        val json = """{"key":123}"""
        val longValue: Long = json.decodeObject<Map<String, Long>>()["key"]!!
        assertEquals(123L, longValue)
    }

    @Test
    fun decodeObject_handlesValidPrimitiveTypeFloat() {
        val json = """{"key":123.45}"""
        val floatValue: Float = json.decodeObject<Map<String, Float>>()["key"]!!
        assertEquals(123.45f, floatValue)
    }

    @Test
    fun decodeObject_handlesValidPrimitiveTypeDouble() {
        val json = """{"key":123.45}"""
        val doubleValue: Double = json.decodeObject<Map<String, Double>>()["key"]!!
        assertEquals(123.45, doubleValue)
    }

    @Test
    fun decodeObject_handlesValidBigInteger() {
        val json = """{"key":12345678901234567890}"""
        val bigIntegerValue: BigInteger = json.decodeObject<Map<String, BigInteger>>()["key"]!!
        assertEquals(BigInteger("12345678901234567890"), bigIntegerValue)
    }

    @Test
    fun decodeObject_handlesValidBigDecimal() {
        val json = """{"key":123.45678901234567890}"""
        val bigDecimalValue: BigDecimal = json.decodeObject<Map<String, BigDecimal>>()["key"]!!
        assertEquals(BigDecimal("123.45678901234567890"), bigDecimalValue)
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidString() {
        val json = """{"key":123}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, String>>()
        }
        assertTrue(exception.message!!.contains("Expected String"))
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidBoolean() {
        val json = """{"key":"true"}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, Boolean>>()["key"]
        }
        assertTrue(exception.message!!.contains("Expected Boolean"))
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidByte() {
        val json = """{"key":"123"}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, Byte>>()
        }
        assertTrue(exception.message!!.contains("Expected Byte"))
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidShort() {
        val json = """{"key":"123"}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, Short>>()
        }
        assertTrue(exception.message!!.contains("Expected Short"))
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidInt() {
        val json = """{"key":"123"}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, Int>>()
        }
        assertTrue(exception.message!!.contains("Expected Int"))
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidLong() {
        val json = """{"key":"123"}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, Long>>()
        }
        assertTrue(exception.message!!.contains("Expected Long"))
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidFloat() {
        val json = """{"key":"123.45"}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, Float>>()
        }
        assertTrue(exception.message!!.contains("Expected Float"))
    }

    @Test
    fun decodeObject_throwsExceptionForInvalidDouble() {
        val json = """{"key":"123.45"}"""
        val exception = assertThrows<JsonParseException> {
            json.decodeObject<Map<String, Double>>()
        }
        assertTrue(exception.message!!.contains("Expected Double"))
    }

    @Test
    fun decodeObject_handlesClassTypeSerializationAndDeserialization() {
        val clazz = String::class.java
        val json = clazz.encodeObject()
        val deserializedClass: Class<*> = json.decodeObject()
        assertEquals(clazz, deserializedClass)
    }
}