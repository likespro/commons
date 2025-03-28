package eth.likespro.commons.hashing

import java.security.MessageDigest
import java.util.*

class StringHashUtils {
    companion object{
        fun String.sha256(): ByteArray {
            return MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
        }
        fun String.sha256Hex(): String {
            return this.sha256().joinToString("") { "%02x".format(it) }
        }
        fun String.sha256Base64(): String {
            return Base64.getUrlEncoder().withoutPadding().encodeToString(this.sha256())
        }
    }
}