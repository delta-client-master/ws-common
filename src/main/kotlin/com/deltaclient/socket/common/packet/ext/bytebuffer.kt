package com.deltaclient.socket.common.packet.ext

import java.nio.ByteBuffer

fun ByteBuffer.putString(string: String) {
    val bytes = string.encodeToByteArray()
    putInt(bytes.size)
    put(bytes)
}

fun ByteBuffer.getString(len: Int): String {
    val bytes = ByteArray(len)
    get(bytes)
    return bytes.decodeToString()
}