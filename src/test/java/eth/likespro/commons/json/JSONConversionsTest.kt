import eth.likespro.commons.json.JSONConversions.Companion.toJSONArray
import eth.likespro.commons.json.JSONConversions.Companion.toJSONObject
import eth.likespro.commons.json.JSONConversions.Companion.toObject
import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.json.JSONObject

class JSONConversionsTest {
    // Any.toJSONObject()
    @Test
    fun toJSONObject_withValidObject_returnsCorrectJSONObject() {
        val data = mapOf("key" to "value")
        val jsonObject = data.toJSONObject()
        assertEquals("{\"key\":\"value\"}", jsonObject.toString())
    }

    @Test
    fun toJSONObject_withEmptyObject_returnsEmptyJSONObject() {
        val data = emptyMap<String, String>()
        val jsonObject = data.toJSONObject()
        assertEquals("{}", jsonObject.toString())
    }

    @Test
    fun toJSONObject_withNestedObject_returnsCorrectJSONObject() {
        val data = mapOf("key" to mapOf("nestedKey" to "nestedValue"))
        val jsonObject = data.toJSONObject()
        assertEquals("{\"key\":{\"nestedKey\":\"nestedValue\"}}", jsonObject.toString())
    }

    @Test
    fun toJSONObject_withNullValue_returnsEmptyJSONObject() {
        val data = mapOf("key" to null)
        val jsonObject = data.toJSONObject()
        assertEquals("{}", jsonObject.toString())
    }

    // Iterable<*>.toJSONArray()

    @Test
    fun toJSONArray_withValidIterable_returnsCorrectJSONArray() {
        val data = listOf("value1", "value2")
        val jsonArray = data.toJSONArray()
        assertEquals("[\"value1\",\"value2\"]", jsonArray.toString())
    }

    @Test
    fun toJSONArray_withEmptyIterable_returnsEmptyJSONArray() {
        val data = emptyList<String>()
        val jsonArray = data.toJSONArray()
        assertEquals("[]", jsonArray.toString())
    }

    @Test
    fun toJSONArray_withNestedIterable_returnsCorrectJSONArray() {
        val data = listOf(listOf("nestedValue1", "nestedValue2"))
        val jsonArray = data.toJSONArray()
        assertEquals("[[\"nestedValue1\",\"nestedValue2\"]]", jsonArray.toString())
    }

    @Test
    fun toJSONArray_withNullValueInIterable_returnsJSONArrayWithNull() {
        val data = listOf(null, "value")
        val jsonArray = data.toJSONArray()
        assertEquals("[null,\"value\"]", jsonArray.toString())
    }

    // Array<*>.toJSONArray()

    @Test
    fun toJSONArray_withValidArray_returnsCorrectJSONArray() {
        val data = arrayOf("value1", "value2")
        val jsonArray = data.toJSONArray()
        assertEquals("[\"value1\",\"value2\"]", jsonArray.toString())
    }

    @Test
    fun toJSONArray_withEmptyArray_returnsEmptyJSONArray() {
        val data = emptyArray<String>()
        val jsonArray = data.toJSONArray()
        assertEquals("[]", jsonArray.toString())
    }

    @Test
    fun toJSONArray_withNestedArray_returnsCorrectJSONArray() {
        val data = arrayOf(arrayOf("nestedValue1", "nestedValue2"))
        val jsonArray = data.toJSONArray()
        assertEquals("[[\"nestedValue1\",\"nestedValue2\"]]", jsonArray.toString())
    }

    @Test
    fun toJSONArray_withNullValueInArray_returnsJSONArrayWithNull() {
        val data = arrayOf(null, "value")
        val jsonArray = data.toJSONArray()
        assertEquals("[null,\"value\"]", jsonArray.toString())
    }

    // JSONObject.toObject()

    @Test
    fun toObject_withValidJSONObject_returnsCorrectObject() {
        val jsonObject = JSONObject("{\"key\":\"value\"}")
        val result: Map<String, String> = jsonObject.toObject()
        assertEquals(mapOf("key" to "value"), result)
    }

    @Test
    fun toObject_withEmptyJSONObject_returnsEmptyObject() {
        val jsonObject = JSONObject("{}")
        val result: Map<String, String> = jsonObject.toObject()
        assertEquals(emptyMap<String, String>(), result)
    }

    @Test
    fun toObject_withNestedJSONObject_returnsCorrectObject() {
        val jsonObject = JSONObject("{\"key\":{\"nestedKey\":\"nestedValue\"}}")
        val result: Map<String, Map<String, String>> = jsonObject.toObject()
        assertEquals(mapOf("key" to mapOf("nestedKey" to "nestedValue")), result)
    }

    @Test
    fun toObject_withNullValueInJSONObject_returnsObjectWithNull() {
        val jsonObject = JSONObject("{\"key\":null}")
        val result: Map<String, Any?> = jsonObject.toObject()
        assertEquals(mapOf("key" to null), result)
    }

    // JSONArray.toObject()

    @Test
    fun toObject_withValidJSONArray_returnsCorrectObject() {
        val jsonArray = JSONArray("[\"value1\", \"value2\"]")
        val result: List<String> = jsonArray.toObject()
        assertEquals(listOf("value1", "value2"), result)
    }

    @Test
    fun toObject_withEmptyJSONArray_returnsEmptyObject() {
        val jsonArray = JSONArray("[]")
        val result: List<String> = jsonArray.toObject()
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun toObject_withNestedJSONArray_returnsCorrectObject() {
        val jsonArray = JSONArray("[[\"nestedValue1\", \"nestedValue2\"]]")
        val result: List<List<String>> = jsonArray.toObject()
        assertEquals(listOf(listOf("nestedValue1", "nestedValue2")), result)
    }

    @Test
    fun toObject_withNullValueInJSONArray_returnsObjectWithNull() {
        val jsonArray = JSONArray("[null, \"value\"]")
        val result: List<Any?> = jsonArray.toObject()
        assertEquals(listOf(null, "value"), result)
    }
}