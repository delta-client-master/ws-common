package com.deltaclient.socket.common.packet.user

import com.deltaclient.socket.common.model.UserStatus
import com.deltaclient.socket.common.packet.IPacket
import java.nio.ByteBuffer

data class C2SUpdateUserStatusPacket(val status: UserStatus) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SUpdateUserStatusPacket, buffer: ByteBuffer ->
            buffer.putInt(it.status.ordinal)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{ it: ByteBuffer ->
            return@deserializer C2SUpdateUserStatusPacket(UserStatus.values()[it.int])
        }
    }
}