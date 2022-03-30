package com.deltaclient.socket.common.packet.ext

import java.nio.ByteBuffer

typealias Serializer<T> = (T, ByteBuffer) -> ByteBuffer
typealias Deserializer<T> = (ByteBuffer) -> T