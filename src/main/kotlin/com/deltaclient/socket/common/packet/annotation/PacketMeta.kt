package com.deltaclient.socket.common.packet.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PacketMeta(val name: String)