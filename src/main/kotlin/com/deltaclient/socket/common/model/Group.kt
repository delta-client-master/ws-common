package com.deltaclient.socket.common.model

import java.util.*

data class Group(
    val id: UUID,
    var title: String,
    var owner: UUID,
    val members: MutableSet<UUID>,
    val messages: MutableSet<Message>
)