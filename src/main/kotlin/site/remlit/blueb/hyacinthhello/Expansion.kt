package site.remlit.blueb.hyacinthhello

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class Expansion : PlaceholderExpansion() {
    override fun getIdentifier(): String = "hyacinthhello"
    override fun getAuthor(): String = "blueb"
    override fun getVersion(): String = "2.0.0"

    override fun onPlaceholderRequest(player: Player?, params: String): String? {
        val split = params.split("_")
        val player = HyacinthHello.instance.server.getPlayer(split.getOrNull(1) ?: "") ?: player

        if (player == null) return null

        fun getMsg(type: String): String? {
            val isClean = type.endsWith("-clean")
            val type = type.replace("-clean", "")

            var message = Storage.get(player.uniqueId.toString(), "$type.msg")

            if (message.isNullOrBlank())
                return null

            if (!player.hasPermission("hyacinthhello.${type}msg"))
                return null

            if (!player.hasPermission("hyacinthhello.color") || isClean)
                message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message))

            return ChatColor.translateAlternateColorCodes('&', "$message")
        }

        when (split[0]) {
            "join" -> return getMsg("join")
            "leave" -> return getMsg("join")
            "quit" -> return getMsg("join")
        }

        return null
    }
}