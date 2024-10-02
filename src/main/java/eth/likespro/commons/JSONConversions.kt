package eth.likespro.commons

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type


class JSONConversions {
    companion object{
        fun Any.toJSONObject(): JSONObject {
            return JSONObject(Gson().toJson(this))
        }
        fun Iterable<*>.toJSONArray(): JSONArray {
            return JSONArray(Gson().toJson(this))
        }
        fun <T> JSONObject.toObject(clazz: Class<T>): T{
            return Gson().fromJson(this.toString(), clazz) as T
        }
        fun <T> JSONArray.toObject(clazz: Class<T>): T{
            val listType: Type = object : TypeToken<T?>() {}.type
            return Gson().fromJson(this.toString(), listType) as T
        }
    }
}