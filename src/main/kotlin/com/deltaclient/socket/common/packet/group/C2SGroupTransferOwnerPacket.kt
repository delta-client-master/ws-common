package com.deltaclient.socket.common.packet.group

import com.deltaclient.socket.common.packet.IPacket
import java.nio.ByteBuffer
import java.util.*

data class C2SGroupTransferOwnerPacket(val id: UUID, val newOwner: UUID) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SGroupTransferOwnerPacket, buffer: ByteBuffer ->
            buffer.putLong(it.id.mostSignificantBits)
            buffer.putLong(it.id.leastSignificantBits)
            buffer.putLong(it.newOwner.mostSignificantBits)
            buffer.putLong(it.newOwner.leastSignificantBits)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{it: ByteBuffer ->
            val uuid = UUID(it.long, it.long)
            val newOwner = UUID(it.long, it.long)
            return@deserializer C2SGroupTransferOwnerPacket(uuid, newOwner)
        }
    }
}