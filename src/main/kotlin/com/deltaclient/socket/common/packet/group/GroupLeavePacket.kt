package com.deltaclient.socket.common.packet.group

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import java.nio.ByteBuffer
import java.util.*

@PacketMeta("c2s-group-leave")
data class C2SGroupLeavePacket(val id: UUID) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SGroupLeavePacket, buffer: ByteBuffer ->
            buffer.putLong(it.id.mostSignificantBits)
            buffer.putLong(it.id.leastSignificantBits)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{it: ByteBuffer ->
            val uuid = UUID(it.long, it.long)
            return@deserializer C2SGroupLeavePacket(uuid)
        }
    }
}