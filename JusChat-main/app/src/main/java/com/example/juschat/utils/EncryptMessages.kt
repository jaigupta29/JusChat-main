package com.example.juschat.utils


import android.os.Build
import androidx.annotation.RequiresApi
import java.io.IOException
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
class EncryptMessages {

    var privateKey: PrivateKey
    var publicKey: PublicKey

    companion object {
        // convert String publickey to Key object
        @RequiresApi(Build.VERSION_CODES.O)
        @Throws(GeneralSecurityException::class, IOException::class)
        fun loadPublicKey(stored: String): Key {
            val data: ByteArray = Base64.getDecoder().
            decode(stored.toByteArray())
            val spec = X509EncodedKeySpec(data)
            val fact = KeyFactory.getInstance("RSA")
            return fact.generatePublic(spec)
        }

        // Encrypt using publickey
        @RequiresApi(Build.VERSION_CODES.O)
        @Throws(Exception::class)
        fun encryptMessage(plainText: String, publickey: String): String {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(publickey))
            return Base64.getEncoder().encodeToString(cipher.doFinal
                (plainText.toByteArray()))
        }

        // Decrypt using privatekey
        @RequiresApi(Build.VERSION_CODES.O)
        @Throws(Exception::class)
        fun decryptMessage(encryptedText: String?, privatekey: String):
                String {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey(privatekey))
            return String(cipher.doFinal(Base64.getDecoder().
            decode(encryptedText)))
        }

        // Convert String private key to privateKey object
        @RequiresApi(Build.VERSION_CODES.O)
        @Throws(GeneralSecurityException::class)
        fun loadPrivateKey(key64: String): PrivateKey {
            val clear: ByteArray = Base64.getDecoder().
            decode(key64.toByteArray())
            val keySpec = PKCS8EncodedKeySpec(clear)
            val fact = KeyFactory.getInstance("RSA")
            val priv = fact.generatePrivate(keySpec)
            Arrays.fill(clear, 0.toByte())
            return priv
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @Throws(Exception::class)
        @JvmStatic
        fun encrypt(message: String):String {
            val keyPairGenerator = EncryptMessages()
            // Generate private and public key
            val privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANHdZHjuChS2Icoc+eUtPNLlhjZXgqPiy0o744sG/lBWdYyFamVcEBt28ExMxMtsznVrLQ9vz6V1eTNcwsuwjIfHePp4Vnedinc7U4CjqmhCY+tZab94iugJDLfh6Jbf7aDRcwuzbUK41VQwDWQcw5zcgIE0njfT3ULH/hqcSra9AgMBAAECgYBzu2CF49eBVnNJzzLr9Ed/kf2yiA3OLOqotGAmMiQaz6MhbA2heeSUEMIxgYBhIk60p/cAdUuQVjkRXw05YNINqwRuc4+/3pyNXXJ3EfyVLvjLePmdB2UFHvi+31WoXpbX1NNb8lf25G+5f58cgHsapjgWpGO9Bq2afPne1sZR4QJBAOpis/7Ytpbu68IiyukU8rNk4u+LtQXjyf3hX6NuccixSYurDTkmcPLOIJnXY9qmU2RFJjgP8bPHNo2QQyYRgVkCQQDlN8/MxNqSrZ8qSGLLJ37tCtECUnLn/tr6demUfJkLMxZI96cOi9v99DuTCISQdZDq1BS1hWNwwQ9ycuomdrAFAkApqyB6xwY28QTCv7K5GztGf0IE+h5VjLiFRQLeqCzcVABSLzermFTuJY0QLIWZAobCxbRUtSjwIBNnuWTmqKgJAkEAuvfAb0WvB+/JIYMz2oQX2yB0hhFcmvHeCmg9pBnR+DmulswzHwFj64zZP0C2aOMM1w8w6TOpfiJsCC3F4qPzRQJBAOQ6kJX8rEYAte9UI27IJ1o/JPGlatajCnHsJmzgHvkGhwqFVi9ip2DLBTAr2yRhWXnVp99tPlHTm3DY/yM/a7E="
            val publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIilKLkkZo3bIXTriWseYBRTP9bmTFDT7IAacxFd9jSxjLkriqx8Vrb2LqB4Fyo/rhgrIq9iIvbrW+n/m3sUsgkCAwEAAQ=="

            val encryptedValue = encryptMessage(message, publicKey)
            return encryptedValue

        }

        @RequiresApi(Build.VERSION_CODES.O)
        @Throws(Exception::class)
        @JvmStatic
        fun decrypt(message: String):String {
            val keyPairGenerator = EncryptMessages()
            // Generate private and public key
            val privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAiKUouSRmjdshdOuJax5gFFM/1uZMUNPsgBpzEV32NLGMuSuKrHxWtvYuoHgXKj+uGCsir2Ii9utb6f+bexSyCQIDAQABAkB4mVOsu5yrnHgVyf+5g9CGf8b7MryuJaw4j59493XhoHunK7yGskKc31HOvKg3bBOuXex/GoUJxovGQmPX45IRAiEAxIjOr/Sw/GUFOs/zy/bNmlHi6XEFamCEnW4sg3W4JG0CIQCx/XDSMRMT+CqBDxD7Gsh2RqdHovfE+7QrNjwqNLHqjQIhALT8R1pCO7oyTV6boidyYGR8hDn1mscbmwRfMR7eiXUBAiAd9tW8fQiCLyAws/Ge5GZlCwX0WGov3lP5hlrgnBhrrQIhAIDpcBEWrkEgL7XI25hdUL22var9ksK8CjZ5s9NKpCqb"
            val publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDR3WR47goUtiHKHPnlLTzS5YY2V4Kj4stKO+OLBv5QVnWMhWplXBAbdvBMTMTLbM51ay0Pb8+ldXkzXMLLsIyHx3j6eFZ3nYp3O1OAo6poQmPrWWm/eIroCQy34eiW3+2g0XMLs21CuNVUMA1kHMOc3ICBNJ43091Cx/4anEq2vQIDAQAB"

            val decryptedText = decryptMessage(message, privateKey)
            return decryptedText

        }

    }

    init {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(1024)
        val pair = keyGen.generateKeyPair()
        privateKey = pair.private
        publicKey = pair.public
    }
}