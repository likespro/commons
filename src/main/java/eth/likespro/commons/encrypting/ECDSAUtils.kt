package eth.likespro.commons.encrypting

import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.Sign
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.*

class ECDSAUtils {
    companion object {
        fun String.signECDSA(credentials: Credentials): String{
            val signature = Sign.signPrefixedMessage(this.toByteArray(), credentials.ecKeyPair)
            val retval = ByteArray(65)
            System.arraycopy(signature.r, 0, retval, 0, 32)
            System.arraycopy(signature.s, 0, retval, 32, 32)
            System.arraycopy(signature.v, 0, retval, 64, 1)
            return Numeric.toHexString(retval)
        }
        fun String.signerAddressECDSA(originalMessage: String): String {
            var signature = this
            return try {
                if (signature.startsWith("0x")) {
                    signature = signature.substring(2)
                }
                val signatureBytes = Numeric.hexStringToByteArray(signature)
                val signatureData = Sign.SignatureData(
                    signatureBytes[64],
                    Arrays.copyOfRange(signatureBytes, 0, 32),
                    Arrays.copyOfRange(signatureBytes, 32, 64)
                )
                val publicKey: BigInteger = Sign.signedPrefixedMessageToKey(originalMessage.toByteArray(), signatureData)
                "0x" + Keys.getAddress(publicKey)
            } catch (e: Exception) {
                e.printStackTrace()
                "0x0"
            }
        }
    }
}