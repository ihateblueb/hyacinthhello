package site.remlit.blueb.hyacinthhello

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.event.server.PluginDisableEvent
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.io.PrintWriter
import java.net.Socket

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