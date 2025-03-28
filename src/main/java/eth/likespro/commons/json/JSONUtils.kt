package eth.likespro.commons.json

import org.json.JSONObject

class JSONUtils {
    companion object {
        /**
         * Returns the value mapped by the specified key as a String, or null if the key is not present.
         *
         * @param key The key whose associated value is to be fetched.
         * @return The associated value as a String, or null if the key is not found.
         */
        fun JSONObject.getStringOrNull(key: String): String? { return if(this.has(key)) this.getString(key) else null }
    }
}