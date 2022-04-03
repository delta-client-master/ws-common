package com.deltaclient.socket.common.packet.cosmetic

import com.deltaclient.socket.common.packet.IPacket

data class C2SCosmeticTogglePacket(val id: String, val state: Boolean) : IPacket