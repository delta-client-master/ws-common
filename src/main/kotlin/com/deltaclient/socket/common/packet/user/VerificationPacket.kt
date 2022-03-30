package com.deltaclient.socket.common.packet.user

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.util.CryptoUtil
import java.nio.ByteBuffer
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

// LOGIN STEPS ->
//  Add name header to the http request for the websocket upgrade containing the username of the minecraft user

// Server sends S2CVerificationRequestPacket to verify that the user connecting actually owns the minecraft account
// For this we are using the same method that the Minecraft Server uses

// Client gets the server ID hash using the public key and an empty secret key and uses Mojang's Authlib to "join" the server

class S2CVerificationRequestPacket(val publicKey: PublicKey, val verifyToken: ByteArray) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: S2CVerificationRequestPacket, buffer: ByteBuffer ->
            val publicKeyBytes = it.publicKey.encoded
            val publicKeyBytesLen = publicKeyBytes.size

            buffer.putInt(publicKeyBytesLen)
            buffer.put(publicKeyBytes)
            buffer.putInt(it.verifyToken.size)
            buffer.put(it.verifyToken)

            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{ it: ByteBuffer ->
            val publicKeyLen = it.int

            val publicKeyBytes = ByteArray(publicKeyLen)
            it.get(publicKeyBytes)

            val verifyTokenLen = it.int
            val verifyToken = ByteArray(verifyTokenLen)
            it.get(verifyToken)

            return@deserializer S2CVerificationRequestPacket(CryptoUtil.decodePublicKey(publicKeyBytes), verifyToken)
        }
    }
}

// SecretKey is AES
@Suppress("unused")
class C2SVerificationResponsePacket : IPacket {
    private val secretEncrypted: ByteArray
    private val verifyEncrypted: ByteArray

    constructor(secretKey: SecretKey, publicKey: PublicKey, verifyToken: ByteArray) {
        secretEncrypted = CryptoUtil.encryptData(publicKey, secretKey.encoded)
        verifyEncrypted = CryptoUtil.encryptData(publicKey, verifyToken)
    }

    private constructor(secretEncrypted: ByteArray, verifyEncrypted: ByteArray) {
        this.secretEncrypted = secretEncrypted
        this.verifyEncrypted = verifyEncrypted
    }

    companion object {
        val SERIALIZER = serializer@{ it: C2SVerificationResponsePacket, buffer: ByteBuffer ->
            val secretBytes = it.secretEncrypted
            val secretBytesLen = secretBytes.size

            buffer.putInt(secretBytesLen)
            buffer.put(secretBytes)

            val verifyBytes = it.verifyEncrypted
            val verifyBytesLen = verifyBytes.size

            buffer.putInt(verifyBytesLen)
            buffer.put(verifyBytes)

            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{ it: ByteBuffer ->
            val secretBytesLen = it.int
            val secretBytes = ByteArray(secretBytesLen)
            it.get(secretBytes)

            val verifyBytesLen = it.int
            val verifyBytes = ByteArray(verifyBytesLen)
            it.get(verifyBytes)

            return@deserializer C2SVerificationResponsePacket(secretBytes, verifyBytes)
        }
    }

    fun getSecretKey(key: PrivateKey): SecretKey {
        return CryptoUtil.decryptSharedKey(key, secretEncrypted)
    }
}
