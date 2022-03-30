package com.deltaclient.socket.common.packet.group

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import java.nio.ByteBuffer
import java.util.*

enum class GroupUpdateType {
    INVITE, KICK
}

@PacketMeta("c2s-group-update-user")
data class C2SGroupUpdateUser(val id: UUID, val type: GroupUpdateType) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SGroupUpdateUser, buffer: ByteBuffer ->
            buffer.putLong(it.id.mostSignificantBits)
            buffer.putLong(it.id.leastSignificantBits)
            buffer.putInt(it.type.ordinal)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{it: ByteBuffer ->
            val uuid = UUID(it.long, it.long)
            val type = GroupUpdateType.values()[it.int]
            return@deserializer C2SGroupUpdateUser(uuid, type)
        }
    }
}