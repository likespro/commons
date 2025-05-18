package eth.likespro.commons.json

import eth.likespro.commons.json.JSONUtils.getStringOrNull
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JSONUtilsTest {
    @Test
    fun getStringOrNull_returnsValue_whenKeyExists() {
        val jsonObject = JSONObject("""{"key1": "value1"}""")
        val result = jsonObject.getStringOrNull("key1")
        assertEquals("value1", result)
    }

    @Test
    fun getStringOrNull_returnsNull_whenKeyDoesNotExist() {
        val jsonObject = JSONObject("""{"key1": "value1"}""")
        val result = jsonObject.getStringOrNull("key2")
        assertEquals(null, result)
    }

    @Test
    fun getStringOrNull_returnsNull_whenJSONObjectIsEmpty() {
        val jsonObject = JSONObject("{}")
        val result = jsonObject.getStringOrNull("key1")
        assertEquals(null, result)
    }

    @Test
    fun getStringOrNull_handlesEdgeCase_whenKeyIsNull() {
        val jsonObject = JSONObject("""{"null": "value1"}""")
        val result = jsonObject.getStringOrNull("null")
        assertEquals("value1", result)
    }
}