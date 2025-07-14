package site.remlit.blueb.hyacinthhello

import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import kotlin.concurrent.thread

class ProxyMessenger {
    companion object {
        var pool: JedisPool? = null

        fun register() {
            val address = HyacinthHello.instance.config.get("proxy-redis.address")?.toString() ?: "0.0.0.0"
            val port = HyacinthHello.instance.config.get("proxy-redis.port")?.toString()?.toInt() ?: 25505
            val ssl = HyacinthHello.instance.config.get("proxy-redis.ssl")?.toString()?.toBoolean() ?: false
            val pass = HyacinthHello.instance.config.get("proxy-redis.pass")?.toString()?.ifBlank { null }
            val jedisConfig = JedisPoolConfig()

            pool = if (!pass.isNullOrBlank()) {
                JedisPool(jedisConfig, address, port, 0, pass.ifBlank { null }, ssl)
            } else JedisPool(jedisConfig, address, port, 0, ssl)
        }

        fun send(messages: List<String>) {
            thread(name = "HyacinthHello Proxy Messenger") {
                if (pool == null) throw Exception("ProxyMessenger has yet to be registered!")
                val message = messages.joinToString("::")
                pool!!.resource.publish(
                    HyacinthHello.instance.config.get("proxy-redis.channel")?.toString() ?: "hyacinthhello",
                    message
                )
            }
        }
    }
}