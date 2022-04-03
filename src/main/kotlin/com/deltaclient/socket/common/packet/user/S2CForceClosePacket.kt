package com.deltaclient.socket.common.packet.user

import com.deltaclient.socket.common.packet.IPacket
import java.nio.ByteBuffer

class S2CForceClosePacket : IPacket {
    companion object {
        val SERIALIZER = serializer@{ _: S2CForceClosePacket, buffer: ByteBuffer ->
            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{ _: ByteBuffer ->
            return@deserializer S2CForceClosePacket()
        }
    }
}