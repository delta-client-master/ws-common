package com.deltaclient.socket.common.packet.friend

import com.deltaclient.socket.common.packet.IPacket
import java.util.*

data class C2SFriendMessagePacket(val id: UUID, val message: String) : IPacket