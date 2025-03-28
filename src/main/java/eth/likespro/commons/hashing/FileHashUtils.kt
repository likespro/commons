package eth.likespro.commons.hashing

import eth.likespro.commons.numeric.NumericUtils.Companion.toHexString
import java.io.BufferedInputStream
import java.io.File
import java.security.MessageDigest

class FileHashUtils {
    companion object{
        @JvmStatic
        fun File.sha256(bufferSize: Int = 8*1024*1024): String{
            val buffer = ByteArray(bufferSize)
            var count: Int
            val digest = MessageDigest.getInstance("SHA-256")
            val bis = BufferedInputStream(this.inputStream())
            while (bis.read(buffer).also { count = it } > 0) {
                digest.update(buffer, 0, count)
            }
            bis.close()

            val hash = digest.digest()
            return "0x"+hash.toHexString()
        }
    }
}