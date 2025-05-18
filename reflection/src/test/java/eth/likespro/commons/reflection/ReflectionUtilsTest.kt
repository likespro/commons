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

import eth.likespro.commons.reflection.ReflectionUtils.boxed
import eth.likespro.commons.reflection.ReflectionUtils.getParametrizedType
import eth.likespro.commons.reflection.ReflectionUtils.getType
import eth.likespro.commons.reflection.ReflectionUtils.unboxed
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.reflect.Type

class ReflectionUtilsTest {
    @Test
    fun boxed_returnsBoxedTypeForPrimitiveBoolean() {
        val primitiveType: Type = java.lang.Boolean.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Boolean::class.java, boxedType)
    }

    @Test
    fun boxed_returnsBoxedTypeForPrimitiveByte() {
        val primitiveType: Type = java.lang.Byte.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Byte::class.java, boxedType)
    }

    @Test
    fun boxed_returnsBoxedTypeForPrimitiveShort() {
        val primitiveType: Type = java.lang.Short.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Short::class.java, boxedType)
    }

    @Test
    fun boxed_returnsBoxedTypeForPrimitiveChar() {
        val primitiveType: Type = java.lang.Character.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Character::class.java, boxedType)
    }

    @Test
    fun boxed_returnsBoxedTypeForPrimitiveInt() {
        val primitiveType: Type = java.lang.Integer.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Integer::class.java, boxedType)
    }

    @Test
    fun boxed_returnsBoxedTypeForPrimitiveLong() {
        val primitiveType: Type = java.lang.Long.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Long::class.java, boxedType)
    }

    @Test
    fun boxed_returnsBoxedTypeForPrimitiveFloat() {
        val primitiveType: Type = java.lang.Float.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Float::class.java, boxedType)
    }

    @Test
    fun boxed_returnsBoxedTypeForPrimitiveDouble() {
        val primitiveType: Type = java.lang.Double.TYPE
        val boxedType = primitiveType.boxed()
        assertEquals(java.lang.Double::class.java, boxedType)
    }

    @Test
    fun boxed_returnsSameTypeForNonPrimitive() {
        val nonPrimitiveType: Type = String::class.java
        val boxedType = nonPrimitiveType.boxed()
        assertEquals(String::class.java, boxedType)
    }

    @Test
    fun boxed_handlesEdgeCaseForNullType() {
        val nullType: Type? = null
        val boxedType = nullType?.boxed()
        assertEquals(null, boxedType)
    }



    @Test
    fun unboxed_returnsUnboxedTypeForBoxedBoolean() {
        val boxedType: Type = java.lang.Boolean::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Boolean.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsUnboxedTypeForBoxedByte() {
        val boxedType: Type = java.lang.Byte::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Byte.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsUnboxedTypeForBoxedShort() {
        val boxedType: Type = java.lang.Short::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Short.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsUnboxedTypeForBoxedChar() {
        val boxedType: Type = java.lang.Character::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Character.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsUnboxedTypeForBoxedInt() {
        val boxedType: Type = java.lang.Integer::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Integer.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsUnboxedTypeForBoxedLong() {
        val boxedType: Type = java.lang.Long::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Long.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsUnboxedTypeForBoxedFloat() {
        val boxedType: Type = java.lang.Float::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Float.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsUnboxedTypeForBoxedDouble() {
        val boxedType: Type = java.lang.Double::class.java
        val unboxedType = boxedType.unboxed()
        assertEquals(java.lang.Double.TYPE, unboxedType)
    }

    @Test
    fun unboxed_returnsSameTypeForNonBoxedType() {
        val nonBoxedType: Type = String::class.java
        val unboxedType = nonBoxedType.unboxed()
        assertEquals(String::class.java, unboxedType)
    }

    @Test
    fun unboxed_handlesEdgeCaseForNullType() {
        val nullType: Type? = null
        val unboxedType = nullType?.unboxed()
        assertEquals(null, unboxedType)
    }



    @Test
    fun getParametrizedType_createsParameterizedTypeWithSingleTypeArgument() {
        val parametrizedType = List::class.java.getParametrizedType(String::class.java)
        assertEquals("java.util.List<java.lang.String>", parametrizedType.typeName)
    }

    @Test
    fun getParametrizedType_createsParameterizedTypeWithMultipleTypeArguments() {
        val parametrizedType = Map::class.java.getParametrizedType(String::class.java, Integer::class.java)
        assertEquals("java.util.Map<java.lang.String, java.lang.Integer>", parametrizedType.typeName)
    }

    @Test
    fun getParametrizedType_handlesEmptyTypeArguments() {
        val parametrizedType = java.lang.Integer::class.java.getParametrizedType()
        assertEquals("java.lang.Integer", parametrizedType.typeName)
    }

    @Test
    fun getParametrizedType_throwsErrorWhenTypeArgumentsAreNotSuitable() {
        assertThrows<IllegalArgumentException> { List::class.java.getParametrizedType() }
    }



    @Test
    fun getType_returnsCorrectTypeForPrimitive() {
        val type = getType<Int>()
        assertEquals("java.lang.Integer", type.typeName)
    }

    @Test
    fun getType_returnsCorrectTypeForGenericClass() {
        val type = getType<List<String>>()
        assertEquals("java.util.List<? extends java.lang.String>", type.typeName)
    }

    @Test
    fun getType_returnsCorrectTypeForNestedGenericClass() {
        val type = getType<Map<String, List<Int>>>()
        assertEquals("java.util.Map<java.lang.String, ? extends java.util.List<? extends java.lang.Integer>>", type.typeName)
    }

    @Test
    fun getType_handlesEdgeCaseForRawType() {
        val type = getType<Any>()
        assertEquals("java.lang.Object", type.typeName)
    }

    @Test
    fun getType_handlesEdgeCaseForNullability() {
        val type = getType<String?>()
        assertEquals("java.lang.String", type.typeName)
    }
}