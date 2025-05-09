package eth.likespro.commons.encoding

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ObjectEncoding {
    companion object {
        fun Any.encodeObject(): String {
            return Gson().toJson(this)
        }
        fun<T> String.decodeObject(): T {
            val type: Type = object : TypeToken<T?>() {}.type
            return Gson().fromJson(this, type) as T
        }
        fun<T> String.decodeObject(type: Type): T {
            return Gson().fromJson(this, type) as T
        }
    }
}