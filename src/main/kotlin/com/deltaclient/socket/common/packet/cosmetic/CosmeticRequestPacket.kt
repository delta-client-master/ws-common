package com.deltaclient.socket.common.packet.cosmetic

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import java.util.*

@PacketMeta("c2s-cosmetic-request")
data class C2SCosmeticRequestPacket(val playerId: UUID) : IPacket