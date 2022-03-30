package com.deltaclient.socket.common.packet.group

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import com.deltaclient.socket.common.packet.ext.getString
import com.deltaclient.socket.common.packet.ext.putString
import java.nio.ByteBuffer

@PacketMeta("c2s-group-create")
data class C2SGroupCreatePacket(val title: String) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SGroupCreatePacket, buffer: ByteBuffer ->
            buffer.putString(it.title)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{it: ByteBuffer ->
            return@deserializer C2SGroupCreatePacket(it.getString(it.int))
        }
    }
}