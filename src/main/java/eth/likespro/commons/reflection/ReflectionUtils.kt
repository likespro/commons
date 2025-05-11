package eth.likespro.commons.reflection

import com.google.gson.reflect.TypeToken
import eth.likespro.commons.models.EncodableResult
import java.lang.reflect.Type

class ReflectionUtils {
    companion object {
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
         *  * @param typeArguments The type arguments to be used in the parameterized type.
         *  * @return The parameterized type created using the given class and type arguments.
         */
        fun Class<*>.getParametrizedType(vararg typeArguments: Type): Type {
            return TypeToken.getParameterized(
                this,
                *(typeArguments.map { it.boxed() }).toTypedArray()
            ).type
        }
    }
}