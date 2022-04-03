package com.deltaclient.socket.common.packet

import com.deltaclient.socket.common.packet.exception.MalformedPacketException
import com.deltaclient.socket.common.packet.ext.Deserializer
import com.deltaclient.socket.common.packet.ext.Serializer
import com.deltaclient.socket.common.packet.group.*
import com.deltaclient.socket.common.packet.user.*
import com.deltaclient.socket.common.util.CryptoUtil
import java.nio.ByteBuffer
import kotlin.reflect.KClass

@Suppress("unused")
object PacketProcessor {
    private val classToId = hashMapOf<Class<*>, Long>()

    private val serializers: MutableMap<Long, (IPacket, ByteBuffer) -> ByteBuffer> = hashMapOf()
    private val deserializers: MutableMap<Long, (ByteBuffer) -> IPacket> = hashMapOf()

    init {
        registerSerializer(S2CVerificationRequestPacket.SERIALIZER)
        registerDeserializer(S2CVerificationRequestPacket.DESERIALIZER)

        registerSerializer(C2SVerificationResponsePacket.SERIALIZER)
        registerDeserializer(C2SVerificationResponsePacket.DESERIALIZER)

        registerSerializer(C2SCrashReportPacket.SERIALIZER)
        registerDeserializer(C2SCrashReportPacket.DESERIALIZER)

        registerSerializer(C2SUpdateServerPacket.SERIALIZER)
        registerDeserializer(C2SUpdateServerPacket.DESERIALIZER)

        registerSerializer(C2SUpdateUserStatusPacket.SERIALIZER)
        registerDeserializer(C2SUpdateUserStatusPacket.DESERIALIZER)

        registerSerializer(C2SGroupCreatePacket.SERIALIZER)
        registerDeserializer(C2SGroupCreatePacket.DESERIALIZER)

        registerSerializer(C2SGroupDeletePacket.SERIALIZER)
        registerDeserializer(C2SGroupDeletePacket.DESERIALIZER)

        registerSerializer(C2SGroupLeavePacket.SERIALIZER)
        registerDeserializer(C2SGroupLeavePacket.DESERIALIZER)

        registerSerializer(C2SGroupMessagePacket.SERIALIZER)
        registerDeserializer(C2SGroupMessagePacket.DESERIALIZER)

        registerSerializer(C2SGroupTransferOwnerPacket.SERIALIZER)
        registerDeserializer(C2SGroupTransferOwnerPacket.DESERIALIZER)

        registerSerializer(C2SGroupUpdateUser.SERIALIZER)
        registerDeserializer(C2SGroupUpdateUser.DESERIALIZER)

        registerSerializer(S2CForceClosePacket.SERIALIZER)
        registerDeserializer(S2CForceClosePacket.DESERIALIZER)
    }

    fun serialize(packet: IPacket): ByteBuffer {
        val buffer = ByteBuffer.allocate(2048)
        val id = getId(packet::class.java)
        buffer.putLong(id)

        return serializers[id]!!(packet, buffer)
    }

    @Throws(MalformedPacketException::class)
    fun deserialize(data: ByteBuffer): IPacket {
        val packetId = data.long
        val packetDeserializer = deserializers[packetId] ?: throw MalformedPacketException()
        return packetDeserializer(data)
    }

    @Throws(MalformedPacketException::class)
    fun deserialize(data: ByteBuffer, vararg accepted: KClass<*>): IPacket {
        val packetId = data.long
        if (accepted.none { getId(it.java) == packetId }) {
            throw MalformedPacketException()
        }

        val packetDeserializer = deserializers[packetId] ?: throw MalformedPacketException()
        return packetDeserializer(data)
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T : IPacket> registerSerializer(noinline fn: Serializer<T>) {
        serializers[getId(T::class.java)] = fn as (IPacket, ByteBuffer) -> ByteBuffer
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T : IPacket> registerDeserializer(noinline fn: Deserializer<T>) {
        deserializers[getId(T::class.java)] = fn as (ByteBuffer) -> IPacket
    }

    private fun getId(packetClass: Class<*>): Long {
        return classToId.getOrPut(packetClass) {
            return@getOrPut CryptoUtil.fnv(packetClass.name)
        }
    }
}