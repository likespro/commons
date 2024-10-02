package eth.likespro.commons.models

/**
 * Object that can be loaded using ObjectLoader
 * @see eth.likespro.commons.ObjectLoader
 */
open class LoadableObject(
    var lockedBy: Long = 0,
    var modified: Boolean = false
) {
    fun notifyModified(){ modified = true }
}