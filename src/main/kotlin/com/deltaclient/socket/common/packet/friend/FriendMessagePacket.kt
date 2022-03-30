package com.deltaclient.socket.common.packet.friend

import com.deltaclient.socket.common.packet.IPacket
import com.deltaclient.socket.common.packet.annotation.PacketMeta
import java.util.*

@PacketMeta("c2s-friend-message")
data class C2SFriendMessagePacket(val id: UUID, val message: String) : IPacket