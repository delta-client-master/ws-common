package com.deltaclient.socket.common.packet.cosmetic

import com.deltaclient.socket.common.packet.IPacket
import java.util.*

data class C2SCosmeticRequestPacket(val playerId: UUID) : IPacket