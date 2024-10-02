package eth.likespro.commons

import eth.likespro.commons.JSONConversions.Companion.toJSONObject
import eth.likespro.commons.JSONConversions.Companion.toObject
import eth.likespro.commons.JSONFiles.Companion.readJSONObject
import eth.likespro.commons.JSONFiles.Companion.writeJSONObject
import eth.likespro.commons.models.LoadableObject
import java.io.File

class ObjectLoader <T : LoadableObject> (
    val clazz: Class<T>,
    val objectsFilesPrefix: String,
    val objectsFilesSuffix: String = ".json",
    val measureMemory: Boolean = false,
    val maxAllowedMemory: Long = 268435456L //256 MiB
){
    val loaded: HashMap<String, T> = hashMapOf()
    var claimedMemory: Long = 0L
    var lockedBy = mutableMapOf<Long, MutableList<String>>()

    fun tick(){
        val unloadList = mutableListOf<String>()
        loaded.forEach { name, obj ->
            if(obj.modified){ saveObject(name) }
            if(obj.lockedBy == 0L) { unloadList += name }
        }
        unloadList.forEach { unloadObject(it) }
    }
    fun getObject(name: String, rayId: Long): T? {
        if(!loaded.containsKey(name)){
            val obj = loadObject(name)
            if(obj != null) {
                loaded[name] = obj
                if(measureMemory) claimedMemory += ObjectSizeFetcher.getObjectSize(obj)
            }
        }
        return if(loaded.containsKey(name)){
            loaded[name]!!.lockedBy++
            lockedBy.putIfAbsent(rayId, mutableListOf())
            lockedBy[rayId]!!.add(name)
            loaded[name]!!
        } else{
            null
        }
    }
    fun createObject(name: String, from: T, rayId: Long){
        //setJson(objectsFilesPrefix+name+objectsFilesSuffix, toJSON(from)!!)
        loaded[name] = from
        loaded[name]!!.modified = true
        loaded[name]!!.lockedBy++
        lockedBy.putIfAbsent(rayId, mutableListOf())
        lockedBy[rayId]!!.add(name)

        if(measureMemory) claimedMemory += ObjectSizeFetcher.getObjectSize(from)
    }
    fun unlock(name: String, rayId: Long){
        loaded[name]!!.lockedBy--
        lockedBy[rayId]!!.remove(name)
    }
    fun unlockAll(rayId: Long){
        lockedBy[rayId]?.forEach {
            loaded[it]!!.lockedBy--
        }
        lockedBy.remove(rayId)
    }
    fun saveObject(name: String){
        File(objectsFilesPrefix+name+objectsFilesSuffix).writeJSONObject(loaded[name]!!.toJSONObject(), 2)
        loaded[name]!!.modified = false
    }
    private fun unloadObject(name: String){
        if(measureMemory) claimedMemory -= ObjectSizeFetcher.getObjectSize(loaded[name]!!)
        loaded.remove(name)
    }
    private fun loadObject(name: String): T? {
        return try{
            File(objectsFilesPrefix + name + objectsFilesSuffix).readJSONObject().toObject(clazz).apply { lockedBy = 0 }
        }catch (_: Exception) { null }
    }
}