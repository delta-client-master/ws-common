package com.deltaclient.socket.common.packet.user

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import java.nio.ByteBuffer

enum class UserStatus {
    ONLINE,
    IDLE,
    DO_NOT_DISTURB,
    OFFLINE
}

@PacketMeta("c2s-update-user-status")
data class C2SUpdateUserStatusPacket(val status: UserStatus) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SUpdateUserStatusPacket, buffer: ByteBuffer ->
            buffer.putInt(it.status.ordinal)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{it: ByteBuffer ->
            return@deserializer C2SUpdateUserStatusPacket(UserStatus.values()[it.int])
        }
    }
}