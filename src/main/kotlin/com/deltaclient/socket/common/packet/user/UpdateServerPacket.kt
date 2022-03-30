package com.deltaclient.socket.common.packet.user

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import com.deltaclient.socket.common.packet.ext.getString
import com.deltaclient.socket.common.packet.ext.putString
import java.nio.ByteBuffer

@PacketMeta("c2s-update-server")
data class C2SUpdateServerPacket(val serverIp: String) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SUpdateServerPacket, buffer: ByteBuffer ->
            buffer.putString(it.serverIp)
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{ it: ByteBuffer ->
            return@deserializer C2SUpdateServerPacket(it.getString(it.int))
        }
    }
}