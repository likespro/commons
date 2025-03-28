package eth.likespro.commons.encrypting

import eth.likespro.commons.encoding.Base58
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class AESUtils {
    companion object {
        fun SecretKey.encodeToString(): String{
            return Base58.encode(this.encoded)
        }
        fun String.decodeToSecretKey(): SecretKey {
            val decodedSecretKey = Base58.decode(this)
            return SecretKeySpec(decodedSecretKey, 0, decodedSecretKey.size, "AES")
        }

        fun String.encryptAES(secretKey: SecretKey): String {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return Base58.encode(cipher.doFinal(this.toByteArray()))
        }
        fun String.decryptAES(secretKey: SecretKey): String{
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            return String(cipher.doFinal(Base58.decode(this)))
        }
    }
}