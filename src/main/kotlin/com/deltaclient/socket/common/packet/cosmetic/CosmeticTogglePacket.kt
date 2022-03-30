package com.deltaclient.socket.common.packet.cosmetic

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta

@PacketMeta("c2s-cosmetic-toggle")
data class C2SCosmeticTogglePacket(val id: String, val state: Boolean) : IPacket