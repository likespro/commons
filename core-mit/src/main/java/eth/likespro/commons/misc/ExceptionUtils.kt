package eth.likespro.commons.misc

object ExceptionUtils {
    fun <T> tryOrRuntimeException(block: () -> T): T = try {
        block()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}