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

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import eth.likespro.commons.reflection.ReflectionUtils.getType
import java.lang.reflect.Type

object ObjectEncoding {
    private class StrictBooleanDeserializer : JsonDeserializer<Boolean> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Boolean {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isBoolean) {
                throw JsonParseException("Expected Boolean but got: $json")
            }
            return json.asBoolean
        }
    }
    private class StrictByteDeserializer : JsonDeserializer<Byte> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Byte {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Byte but got: $json")
            }
            return json.asByte
        }
    }
    private class StrictShortDeserializer : JsonDeserializer<Short> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Short {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Short but got: $json")
            }
            return json.asShort
        }
    }
    private class StrictIntDeserializer : JsonDeserializer<Int> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Int but got: $json")
            }
            return json.asInt
        }
    }
    private class StrictLongDeserializer : JsonDeserializer<Long> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Long {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Long but got: $json")
            }
            return json.asLong
        }
    }
    private class StrictFloatDeserializer : JsonDeserializer<Float> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Float {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Float but got: $json")
            }
            return json.asFloat
        }
    }
    private class StrictDoubleDeserializer : JsonDeserializer<Double> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Double {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Double but got: $json")
            }
            return json.asDouble
        }
    }
    private class StrictStringDeserializer : JsonDeserializer<String> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isString) {
                throw JsonParseException("Expected String but got: $json")
            }
            return json.asString
        }
    }
    private class ClassTypeAdapter : JsonSerializer<Class<*>>, JsonDeserializer<Class<*>> {
        override fun serialize(
            src: Class<*>,
            typeOfSrc: Type,
            context: JsonSerializationContext
        ): JsonElement {
            return JsonPrimitive(src.name)
        }
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): Class<*> {
            return Class.forName(json.asString)
        }
    }

    private class UnitDeserializer : TypeAdapter<Unit?>() {
        override fun write(out: JsonWriter, value: Unit?) {
            out.nullValue()
        }
        override fun read(reader: JsonReader): Unit? {
            reader.nextNull()
            return null
        }
    }
    private class VoidDeserializer : TypeAdapter<Void?>() {
        override fun write(out: JsonWriter, value: Void?) {
            out.nullValue()
        }
        override fun read(reader: JsonReader): Void? {
            reader.nextNull()
            return null
        }
    }

    private val gson = GsonBuilder()
        // Unboxed
        .registerTypeHierarchyAdapter(java.lang.Boolean.TYPE, StrictBooleanDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Byte.TYPE, StrictByteDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Short.TYPE, StrictShortDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Integer.TYPE, StrictIntDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Long.TYPE, StrictLongDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Float.TYPE, StrictFloatDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Double.TYPE, StrictDoubleDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Void.TYPE, VoidDeserializer())
        //Boxed
        .registerTypeHierarchyAdapter(java.lang.Boolean::class.java, StrictBooleanDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Byte::class.java, StrictByteDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Short::class.java, StrictShortDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Integer::class.java, StrictIntDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Long::class.java, StrictLongDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Float::class.java, StrictFloatDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Double::class.java, StrictDoubleDeserializer())
        .registerTypeHierarchyAdapter(java.lang.String::class.java, StrictStringDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Class::class.java, ClassTypeAdapter())
        .registerTypeHierarchyAdapter(Unit::class.java, UnitDeserializer())
        .registerTypeHierarchyAdapter(java.lang.Void::class.java, VoidDeserializer())
        .create()

    /**
     * Encodes an object to a string.
     *
     * @return The string representation of the object.
     */
    fun Any.encodeObject(): String {
        return gson.toJson(this)
    }

    /**
     * Decodes a string to an object of the specified type.
     *
     * @param T The type of the object to decode.
     * @return The decoded object.
     */
    inline fun<reified T> String.decodeObject(): T = this.decodeObject(getType<T>()) as T //awd

    /**
     * Decodes a string to an object of the specified type.
     *
     * @param type The type of the object to decode.
     * @return The decoded object.
     */
    fun String.decodeObject(type: Type): Any {
        return gson.fromJson(this, type)
    }
}