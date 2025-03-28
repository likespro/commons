package eth.likespro.commons.mongodb

import org.bson.Document
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DocumentUtils {
    companion object{
        /**
         * Safely retrieves a field from the Document as a Long.
         * Converts the field value to Long if it is an Int.
         * Throws an exception if the field is not of type Int or Long.
         *
         * @param field The name of the field to retrieve from the document.
         * @return The value of the specified field as a Long.
         * @throws IllegalArgumentException If the field value is not of type Int or Long.
         */
        fun Document.getLongSafely(field: String): Long {
            val value = this[field]
            return when (value) {
                is Int -> value.toLong()
                is Long -> value
                else -> throw IllegalArgumentException("Field $field is not Integer!")
            }
        }

        fun Document.toJSONObject(): JSONObject {
            return JSONObject(this.toJson())
        }
        fun JSONObject.toDocument(): Document {
            return Document.parse(this.toString()).apply {
                this.forEach { key, value ->
                    if(value is String){
                        try{
                            val date = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm:ss a", Locale.ENGLISH))
                            this[key] = date
                        } catch (_: Exception) { }
                    }
                }
            }
        }
    }
}