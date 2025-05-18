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

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object ReflectionUtils {
    /**
     * Converts a type to its corresponding boxed type if it is primitive. Else returns the type itself.
     *
     * @return the boxed type of the type if it is primitive or the type itself.
     */
    fun Type.boxed(): Type = when (this) {
        java.lang.Boolean.TYPE -> java.lang.Boolean::class.java
        java.lang.Byte.TYPE -> java.lang.Byte::class.java
        java.lang.Short.TYPE -> java.lang.Short::class.java
        java.lang.Character.TYPE -> java.lang.Character::class.java
        java.lang.Integer.TYPE -> java.lang.Integer::class.java
        java.lang.Long.TYPE -> java.lang.Long::class.java
        java.lang.Float.TYPE -> java.lang.Float::class.java
        java.lang.Double.TYPE -> java.lang.Double::class.java
        else -> this
    }

    /**
     * Converts a type to its corresponding unboxed type (if exists) if it is boxed. Else returns the type itself.
     *
     * @return the unboxed type (if exists) of the type if it is boxed or the type itself.
     */
    fun Type.unboxed(): Type = when (this) {
        java.lang.Boolean::class.java -> java.lang.Boolean.TYPE
        java.lang.Byte::class.java -> java.lang.Byte.TYPE
        java.lang.Short::class.java -> java.lang.Short.TYPE
        java.lang.Character::class.java -> java.lang.Character.TYPE
        java.lang.Integer::class.java -> java.lang.Integer.TYPE
        java.lang.Long::class.java -> java.lang.Long.TYPE
        java.lang.Float::class.java -> java.lang.Float.TYPE
        java.lang.Double::class.java -> java.lang.Double.TYPE
        else -> this
    }

    /**
     * Creates a parameterized type using the given class and type arguments.
     *
     *  @param typeArguments The type arguments to be used in the parameterized type.
     *  @return The parameterized type created using the given class and type arguments.
     */
    fun Class<*>.getParametrizedType(vararg typeArguments: Type): Type {
        return TypeToken.getParameterized(
            this,
            *(typeArguments.map { it.boxed() }).toTypedArray()
        ).type
    }

    /**
     * Creates a parameterized type using template type arguments.
     *
     * @param T The type to be used in the parameterized type.
     * @return The parameterized type created using template type arguments.
     */
    inline fun <reified T> getType(): Type = object : TypeToken<T>() {}.type
}