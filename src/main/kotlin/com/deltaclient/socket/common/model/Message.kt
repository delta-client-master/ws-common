package com.deltaclient.socket.common.model

import java.util.*

data class Message(val author: UUID, val content: String, val timestamp: Long)