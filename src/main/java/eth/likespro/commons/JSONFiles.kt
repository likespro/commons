package eth.likespro.commons

import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class JSONFiles {
    companion object{
        fun File.readJSONObject(): JSONObject{
            return JSONObject(this.readText())
        }
        fun File.readJSONArray(): JSONArray{
            return JSONArray(this.readText())
        }
        fun File.writeJSONObject(json: JSONObject, indentFactor: Int = 0){
            this.writeText(json.toString(indentFactor))
        }
        fun File.writeJSONArray(json: JSONArray, indentFactor: Int = 0){
            this.writeText(json.toString(indentFactor))
        }
    }
}