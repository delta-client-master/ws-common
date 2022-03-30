package com.deltaclient.socket.common.packet.group

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import com.deltaclient.socket.common.packet.ext.getString
import com.deltaclient.socket.common.packet.ext.putString
import java.nio.ByteBuffer
import java.util.*

@PacketMeta("c2s-group-message")
data class C2SGroupMessagePacket(val id: UUID, val message: String) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SGroupMessagePacket, buffer: ByteBuffer ->
            buffer.putLong(it.id.mostSignificantBits)
            buffer.putLong(it.id.leastSignificantBits)
            buffer.putString(it.message)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{it: ByteBuffer ->
            val uuid = UUID(it.long, it.long)
            val message = it.getString(it.int)
            return@deserializer C2SGroupMessagePacket(uuid, message)
        }
    }
}