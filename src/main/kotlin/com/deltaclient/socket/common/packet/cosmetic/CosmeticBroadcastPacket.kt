package com.deltaclient.socket.common.packet.cosmetic

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta

@PacketMeta("c2s-cosmetic-broadcast")
data class C2SCosmeticBroadcastPacket(val id: String) : IPacket