package com.deltaclient.socket.common.util

import java.math.BigInteger
import java.security.*
import java.security.spec.EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Suppress("MemberVisibilityCanBePrivate", "SameParameterValue", "unused")
object CryptoUtil {
    fun fnv(string: String): Long {
        var sum = 0L
        var product = 0L

        for (c in string.toCharArray()) {
            sum += c.code
            if (product < 2) {
                product += c.code
            } else {
                product *= c.code / 4
            }
        }

        var hash = sum
        for (c in string.toCharArray()) {
            hash = (hash xor c.code.toLong()) * product
        }

        return hash
    }

    fun getServerIdHash(serverId: String, publicKey: PublicKey, secretKey: SecretKey): ByteArray {
        return digestOperation(
            "SHA-1",
            serverId.toByteArray(charset("ISO_8859_1")),
            secretKey.encoded,
            publicKey.encoded
        )
    }

    private fun digestOperation(algorithm: String, vararg data: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance(algorithm)
        for (byte in data) {
            digest.update(byte)
        }
        return digest.digest()
    }

    fun decodePublicKey(encodedKey: ByteArray): PublicKey {
        val keySpec: EncodedKeySpec = X509EncodedKeySpec(encodedKey)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePublic(keySpec)
    }

    fun encryptData(key: Key, data: ByteArray): ByteArray {
        return cipherOperation(1, key, data)
    }

    fun decryptSharedKey(key: PrivateKey, secretKeyEncrypted: ByteArray): SecretKey {
        return SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES")
    }

    fun decryptData(key: Key, data: ByteArray): ByteArray {
        return cipherOperation(2, key, data)
    }

    private fun cipherOperation(opMode: Int, key: Key, data: ByteArray): ByteArray {
        return createTheCipherInstance(opMode, key.algorithm, key).doFinal(data)
    }

    private fun createTheCipherInstance(opMode: Int, transformation: String, key: Key): Cipher {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(opMode, key)
        return cipher
    }

    fun createNewSharedKey(): SecretKey {
        val generator = KeyGenerator.getInstance("AES")
        generator.init(128)
        return generator.generateKey()
    }

    fun getRadixServerId(serverId: String, public: PublicKey, secret: SecretKey): String {
        return BigInteger(
            getServerIdHash(
                serverId,
                public,
                secret
            )
        ).toString(16)
    }
}