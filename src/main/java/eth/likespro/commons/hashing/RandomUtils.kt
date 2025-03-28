package eth.likespro.commons.hashing

import java.security.SecureRandom
import java.util.*

class RandomUtils {
    companion object{
        fun SecureRandom.nextBase64String(bytesNumber: Int): String {
            val bytes = ByteArray(bytesNumber)
            this.nextBytes(bytes)
            val encoder: Base64.Encoder = Base64.getUrlEncoder().withoutPadding()
            val token: String = encoder.encodeToString(bytes)
            return token
        }
    }
}