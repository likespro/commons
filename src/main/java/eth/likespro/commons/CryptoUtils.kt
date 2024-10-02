package eth.likespro.commons

import org.apache.commons.codec.digest.DigestUtils
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.Sign
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateCrtKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


class CryptoUtils {
    companion object{
        fun String.decodeToSecretKey(): SecretKey{
            val decodedSecretKey = Base58.decode(this)
            return SecretKeySpec(decodedSecretKey, 0, decodedSecretKey.size, "AES")
        }
        fun SecretKey.encodeToString(): String{
            return Base58.encode(this.encoded)
        }
        @OptIn(ExperimentalEncodingApi::class)
        fun PrivateKey.encodeToString(): String{
            val privateKeyInfo = PrivateKeyInfo.getInstance(this.encoded)
            val asn1Encodable = privateKeyInfo.parsePrivateKey()
            val asn1Primitive = asn1Encodable.toASN1Primitive()
            val privateKeyPKCS8Formatted = asn1Primitive.getEncoded(ASN1Encoding.DER)
            return Base64.encode(privateKeyPKCS8Formatted)
        }
        @OptIn(ExperimentalEncodingApi::class)
        fun String.decodeToRSAPrivateKey(): PrivateKey{
            val encodedPrivateKey: ByteArray = Base64.decode(this.replace("\n", ""))
            val keySpec = PKCS8EncodedKeySpec(encodedPrivateKey)
            val kf = KeyFactory.getInstance("RSA")
            return kf.generatePrivate(keySpec)
        }
        fun PrivateKey.toPublicKey(): PublicKey{
            val rsaPrivateKey = this as RSAPrivateCrtKey
            val kf = KeyFactory.getInstance("RSA")
            return kf.generatePublic(RSAPublicKeySpec(rsaPrivateKey.modulus, rsaPrivateKey.publicExponent))
        }
        fun String.sha256(): String{
            return DigestUtils.sha256Hex(this)
        }
        fun ByteArray.sha256(): String{
            return DigestUtils.sha256Hex(this)
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
        fun String.encryptRSA(publicKey: PublicKey): String{
            val encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey)
            return Base58.encode(encryptCipher.doFinal(this.toByteArray()))
        }
        fun String.decryptRSA(privateKey: PrivateKey): String{
            val decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey)
            return String(decryptCipher.doFinal(Base58.decode(this)))
        }
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