package site.remlit.blueb.hyacinthhello

import redis.clients.jedis.JedisPool

class ProxyMessenger {
    companion object {
        var pool: JedisPool? = null

        fun register() {
            val address = HyacinthHello.instance.config.get("proxy-redis.address")?.toString() ?: "0.0.0.0"
            val port = HyacinthHello.instance.config.get("proxy-redis.port")?.toString()?.toInt() ?: 25505

            pool = JedisPool(address, port)
        }

        fun send(messages: List<String>) {
            if (pool == null) throw Exception("ProxyMessenger has yet to be registered!")
            val message = messages.joinToString("::")
            pool!!.resource.publish(
                HyacinthHello.instance.config.get("proxy-redis.channel")?.toString() ?: "hyacinthhello",
                message
            )
        }
    }
}