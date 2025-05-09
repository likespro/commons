package eth.likespro.commons.json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

class JSONConversions {
    companion object {
        /**
         * Converts any object to a JSONObject.
         *
         * This function uses Gson to serialize the object to a JSON string and then
         * parses that string into a JSONObject.
         *
         * @return a JSONObject representation of the object.
         */
        fun Any.toJSONObject(): JSONObject {
            return JSONObject(Gson().toJson(this))
        }

        /**
         * Converts any object to a JSONArray.
         *
         * This function uses Gson to serialize the object to a JSON string and then
         * parses that string into a JSONArray.
         *
         * @return a JSONArray representation of the object.
         */
        fun Any.toJSONArray(): JSONArray {
            return JSONArray(Gson().toJson(this))
        }

        /**
         * Converts a JSONObject to an object of type T.
         *
         * This function uses Gson to deserialize the JSONObject to an object of the specified type.
         *
         * @param T the type of the object to be returned.
         * @return an object of type T.
         */
        fun <T> JSONObject.toObject(): T {
            val type: Type = object : TypeToken<T?>() {}.type
            return Gson().fromJson(this.toString(), type) as T
        }

        /**
         * Converts a JSONArray to an object of type T.
         *
         * This function uses Gson to deserialize the JSONArray to an object of the specified type.
         *
         * @param T the type of the object to be returned.
         * @return an object of type T.
         */
        fun <T> JSONArray.toObject(): T {
            val listType: Type = object : TypeToken<T?>() {}.type
            return Gson().fromJson(this.toString(), listType) as T
        }
    }
}