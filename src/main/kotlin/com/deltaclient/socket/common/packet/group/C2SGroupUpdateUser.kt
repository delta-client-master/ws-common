package com.deltaclient.socket.common.packet.group

import com.deltaclient.socket.common.model.GroupUpdateType
import com.deltaclient.socket.common.packet.IPacket
import java.nio.ByteBuffer
import java.util.*

data class C2SGroupUpdateUser(val id: UUID, val type: GroupUpdateType) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SGroupUpdateUser, buffer: ByteBuffer ->
            buffer.putLong(it.id.mostSignificantBits)
            buffer.putLong(it.id.leastSignificantBits)
            buffer.putInt(it.type.ordinal)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{ it: ByteBuffer ->
            val uuid = UUID(it.long, it.long)
            val type = GroupUpdateType.values()[it.int]
            return@deserializer C2SGroupUpdateUser(uuid, type)
        }
    }
}