package eth.likespro.commons

import eth.likespro.commons.ConsoleUtils.Companion.clearConsole
import eth.likespro.commons.CryptoUtils.Companion.decodeToRSAPrivateKey
import eth.likespro.commons.CryptoUtils.Companion.decodeToSecretKey
import eth.likespro.commons.CryptoUtils.Companion.decryptAES
import eth.likespro.commons.CryptoUtils.Companion.decryptRSA
import eth.likespro.commons.CryptoUtils.Companion.encodeToString
import eth.likespro.commons.CryptoUtils.Companion.encryptAES
import eth.likespro.commons.CryptoUtils.Companion.encryptRSA
import eth.likespro.commons.CryptoUtils.Companion.sha256
import eth.likespro.commons.CryptoUtils.Companion.signECDSA
import eth.likespro.commons.CryptoUtils.Companion.signerAddressECDSA
import eth.likespro.commons.CryptoUtils.Companion.toPublicKey
import eth.likespro.commons.JSONConversions.Companion.toJSONObject
import eth.likespro.commons.JSONConversions.Companion.toObject
import eth.likespro.commons.NumericUtils.Companion.toReadableInformationSize
import org.web3j.crypto.WalletUtils
import java.io.File
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

fun main(){
    println("This will be cleared.")
    Thread.sleep(1000)
    clearConsole()

    val objectLoader = ObjectLoader(TestClass::class.java, ".\\", ".json")
    val obj2 = TestClass(777.112)
    objectLoader.createObject("1", TestClass(52.993), 0)
    objectLoader.createObject("2", obj2, 1)
    objectLoader.unlockAll(0)
    objectLoader.tick()
    objectLoader.unlock("2", 1)
    objectLoader.tick()
    println(objectLoader.getObject("2", 0)!!.value.toString() + " == 777.112")

    println(TestClass(52.993).toJSONObject())
    println(TestClass(52.993).toJSONObject().toObject(TestClass::class.java).value.toString() + " == 52.993")

    var count = 0
    val synchronizer = Synchronizer()
    var i = 0
    while (i < 10000){
        Thread{ synchronizer.doSynced { count++ } }.start()
        i++
    }
    Thread.sleep(1000)
    println("$count == 10000")

    val walletFile = WalletUtils.generateNewWalletFile("likespro", File(".\\"))
    val credentials = WalletUtils.loadCredentials(
        "likespro",
        ".\\$walletFile"
    )
    println(credentials.address)
    val signature = "Hello, world".signECDSA(credentials)
    println(signature)
    println(signature.signerAddressECDSA("Hello, world") + " == ${credentials.address}")
    File(".\\$walletFile").delete()
    val secretKey: SecretKey = KeyGenerator.getInstance("AES").generateKey()
    println("Hello, world".encryptAES(secretKey).decryptAES(secretKey.encodeToString().decodeToSecretKey()) + " == Hello, world")
    val privateKey = ("MIIEpQIBAAKCAQEAnuRN4e0Gh3IXOVX9aPzLKTgsW01Ouq7mm6EgOV7ugZEbI1xm\n" +
            "GX6lQBtikVizJvZo51rItFLkeLJ1FuBkMhSRdIOMZLoZ3hu8U8E7rinhkKOwdsne\n" +
            "aPEi1JUMrJ4zQBP2n8ilWMag6HTeGdPaxEpOxvW1bsAT1DodU+sbNCxuQhSAnnvj\n" +
            "O+uxATGdgmPZCl+2KwfNNqoRu3SE3VtiA12M/xTKdNhfyB041wI6fZtlF3Sqgacp\n" +
            "RFu3SAK2PhBu/D+EWj7EjQTlvnxFnndOWbnyRtbfIowdOO8sctPPYbtg3sbC++i3\n" +
            "Fe5n8bv2a7m9Fe53FOepgMIIBJylFYu0Af9OkQIDAQABAoIBAQCF5KlkqtIHrTK4\n" +
            "1Cd5Ix0oHPfz8E9ZrS0TFFINRlwBvv95atF2xmwGe40yIebF/HsmCjB7FCcHNorI\n" +
            "vkpwkTiJUJksAE7wBbtLcNgTyaMUuXtofm/3SswpC3jktWNGqgv3VaNQDgFmIaVP\n" +
            "amE9a8i6jC9Zpm6PNnGkV8hSzOYUs9C5NSA/us6gERekIpK87VzO4hpGZ7vdEHzF\n" +
            "MmEzU/63iqh35OGR9dW2r+2Vs+uTTm/4+EaVOLzbTbyHsC1m4SEtURUIRckyFntz\n" +
            "6RNKYKtEU+01YM2/+KcndR17flEQ7QRtwLU1XXNUERnnmz1JgC5qkX2Bwq/0SQqw\n" +
            "u75BK2KBAoGBAN4W+h6btMj9OUdkT7nxVTxrzNXpkLjpXzp1x4o++qpXW/597ZrK\n" +
            "+iehhpAjISbxeFA2os+aHmKZZUFScyo9O6dh+69IUoqAYsA858WANdkEYK3Ynzpd\n" +
            "8v3Dpgj6ViiTJUCWxjkGQOsuYGLVrJ9Dblqe+bipHyjhumE5IEqPwD2JAoGBALcn\n" +
            "DVrNf5baaNT+oWEsrSoLCPiwgKlkIwWRpNpwQ9YrVffG1NiX04hpt69Cbku5hjmb\n" +
            "M1/z+/XsTTBwLVDzfuhVfW7gIWNpMVl/6VZOhoLCSN5ox3aTa5+wHSqS/REjZPam\n" +
            "V3Zib9KfwU6pivdTXqcMnuL8VHV4YFiZ7Q2MRY7JAoGBAJAf1R076qxsOFmuLVb7\n" +
            "2FanlnxTYIm/WP81I9MBX+ZfXuHeku1bP8+sMQkk4wTEu1FCw5F58LIH3f/wI7CL\n" +
            "oETVf2GWy4n6Gxup6rbupwM4pgzrgRl9ko1ABGXbH0KhixFJR0YGWubEZ+R7ydRp\n" +
            "DBpfBKvjhMchBbTGP8BjXVwRAoGBAKUYqs0jWNPe5Yi6TzaNUzsG33ttf00yWu92\n" +
            "LihJ8pxoTAIiVVM46RGaWl29gDb6cagT4uenw4QXEi2LsQ27sLY1+e+sUeyOslF/\n" +
            "IBMQjefowk7aHJMiqG02D9SE02JTiXtczpKEgVBXeaUy53WyzSvAEKmD045giFjV\n" +
            "x/3iaAEhAoGAERcX72seS4qLBUMof/k2pg3TACZUm/zXAO0iiIwYeWwQrh3WntNF\n" +
            "72dBr0g5vUJ1pIo9sUcSv0Z4PTLix0fvyemDqUeRhuBdikrXxvh8MIu/vOf0erCl\n" +
            "o5yFaBOdW0Lzq5k6Ip7yMNb3LZE015psN5OiNBEAFFybrf4bzMIyF7Y=").decodeToRSAPrivateKey()//.encodeToString().decodeToRSAPrivateKey()
    println(privateKey.encodeToString())
    println("Hello, world".encryptRSA(privateKey.toPublicKey()).decryptRSA(privateKey.encodeToString().decodeToRSAPrivateKey()) + " == Hello, world")
    println("Hello, world".sha256())


    println(ConsoleUtils.colorIndicator(5, 20)+ConsoleUtils.progressIndicator(10, 5, 20, '-', '>', '.')+" 5/20")
    println(ConsoleUtils.colorIndicator(12, 20)+ConsoleUtils.progressIndicator(10, 12, 20, '-', '>', '.')+" 12/20")
    println(ConsoleUtils.colorIndicator(19, 20)+ConsoleUtils.progressIndicator(10, 19, 20, '-', '>', '.')+" 19/20${ConsoleUtils.ANSI_RESET}")
    println(123465.toReadableInformationSize())
}