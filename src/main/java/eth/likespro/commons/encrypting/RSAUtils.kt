package eth.likespro.commons.encrypting

import eth.likespro.commons.encoding.Base58
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateCrtKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class RSAUtils {
    companion object {
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

        @OptIn(ExperimentalEncodingApi::class)
        fun PublicKey.encodeToString(): String{
            return Base64.encode(this.encoded)
        }
        @OptIn(ExperimentalEncodingApi::class)
        fun String.decodeToRSAPublicKey(): PublicKey{
            val publicBytes = Base64.decode(this)
            val keySpec = X509EncodedKeySpec(publicBytes)
            val keyFactory = KeyFactory.getInstance("RSA")
            return keyFactory.generatePublic(keySpec)
        }

        @OptIn(ExperimentalEncodingApi::class)
        fun PrivateKey.toPublicKey(): PublicKey{
            val rsaPrivateKey = this as RSAPrivateCrtKey
            val kf = KeyFactory.getInstance("RSA")
            return kf.generatePublic(RSAPublicKeySpec(rsaPrivateKey.modulus, rsaPrivateKey.publicExponent))
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
    }
}