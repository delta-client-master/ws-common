package com.deltaclient.socket.common.packet.friend

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import java.util.*

@PacketMeta("c2s-friend-remove")
data class C2SFriendRemovePacket(val id: UUID) : IPacket