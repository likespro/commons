package eth.likespro.commons.models

import eth.likespro.commons.JSONConversions.Companion.toJSONObject
import eth.likespro.commons.JSONFiles.Companion.writeJSONObject
import java.io.File

open class AutoSavableObject(
        val source: File,
        var modified: Boolean = false
) {
    fun notifyModified(){ modified = true }
    fun tick(){
        if(modified){
            source.writeJSONObject(this.toJSONObject())
            modified = false
        }
    }
}