package eth.likespro.commons

import kotlin.math.max

class Synchronizer {
    var lock = Any()
    var totalWorkedFor = 0L
    var maxWorkedFor = 0L
    fun <R> doSynced(what: () -> R): R{
        val startedAt = System.currentTimeMillis()
        val res = synchronized(lock, what)
        maxWorkedFor = max(maxWorkedFor, System.currentTimeMillis() - startedAt)
        totalWorkedFor += System.currentTimeMillis() - startedAt
        return res
    }
}