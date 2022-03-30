package com.deltaclient.socket.common.packet.user

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import com.deltaclient.socket.common.packet.ext.getString
import com.deltaclient.socket.common.packet.ext.putString
import java.nio.ByteBuffer

@PacketMeta("c2s-crash-report")
data class C2SCrashReportPacket(val crashReport: String) : IPacket {
    companion object {
        val SERIALIZER = serializer@{ it: C2SCrashReportPacket, buffer: ByteBuffer ->
            buffer.putString(it.crashReport)

            return@serializer buffer
        }

        val DESERIALIZER = deserializer@{ it: ByteBuffer ->
            return@deserializer C2SCrashReportPacket(it.getString(it.int))
        }
    }
}

@PacketMeta("s2c-force-close")
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