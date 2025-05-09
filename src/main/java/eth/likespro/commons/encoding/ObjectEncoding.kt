package eth.likespro.commons.encoding

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ObjectEncoding {
    class StrictBooleanDeserializer : JsonDeserializer<Boolean> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Boolean {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isBoolean) {
                throw JsonParseException("Expected Boolean but got: $json")
            }
            return json.asBoolean
        }
    }
    class StrictByteDeserializer : JsonDeserializer<Byte> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Byte {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Byte but got: $json")
            }
            return json.asByte
        }
    }
    class StrictShortDeserializer : JsonDeserializer<Short> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Short {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Short but got: $json")
            }
            return json.asShort
        }
    }
    class StrictIntDeserializer : JsonDeserializer<Int> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Int but got: $json")
            }
            return json.asInt
        }
    }
    class StrictLongDeserializer : JsonDeserializer<Long> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Long {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Long but got: $json")
            }
            return json.asLong
        }
    }
    class StrictFloatDeserializer : JsonDeserializer<Float> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Float {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Float but got: $json")
            }
            return json.asFloat
        }
    }
    class StrictDoubleDeserializer : JsonDeserializer<Double> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Double {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isNumber) {
                throw JsonParseException("Expected Double but got: $json")
            }
            return json.asDouble
        }
    }
    class StrictStringDeserializer : JsonDeserializer<String> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
            if (!json.isJsonPrimitive || !json.asJsonPrimitive.isString) {
                throw JsonParseException("Expected String but got: $json")
            }
            return json.asString
        }
    }
    class ClassTypeAdapter : JsonSerializer<Class<*>>, JsonDeserializer<Class<*>> {
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

    companion object {
        val gson = GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, StrictBooleanDeserializer())
            .registerTypeAdapter(Byte::class.java, StrictByteDeserializer())
            .registerTypeAdapter(Short::class.java, StrictShortDeserializer())
            .registerTypeAdapter(Int::class.java, StrictIntDeserializer())
            .registerTypeAdapter(Long::class.java, StrictLongDeserializer())
            .registerTypeAdapter(Float::class.java, StrictFloatDeserializer())
            .registerTypeAdapter(Double::class.java, StrictDoubleDeserializer())
            .registerTypeAdapter(String::class.java, StrictStringDeserializer())
            .registerTypeAdapter(Class::class.java, ClassTypeAdapter())
            .create()

        fun Any.encodeObject(): String {
            return gson.toJson(this)
        }
        fun<T> String.decodeObject(): T {
            val type: Type = object : TypeToken<T?>() {}.type
            return gson.fromJson(this, type) as T
        }
        fun String.decodeObject(type: Type): Any {
            return gson.fromJson(this, type)
        }
    }
}