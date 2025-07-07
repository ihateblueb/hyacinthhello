package site.remlit.blueb.hyacinthhello

import com.google.common.io.ByteStreams

class ProxyMessenger {
    companion object {
        val IDENTIFIER = "hyacinthhello:main"

        fun register() {
            HyacinthHello.instance.server.messenger.registerOutgoingPluginChannel(HyacinthHello.instance, IDENTIFIER)
        }

        /**
         * Proxy messages from HyacinthHello must follow this format:
         *
         * `{type}::{player uuid},{player name}::{message OR "null"}`
         * */
        fun send(message: String) {
            val stream = ByteStreams.newDataOutput()
            stream.writeUTF(message)
            HyacinthHello.instance.server.sendPluginMessage(HyacinthHello.instance, IDENTIFIER, message.toByteArray())
        }
    }
}