package site.remlit.blueb.hyacinthhello

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class Listener : Listener {
    @EventHandler()
    fun onPlayerJoin(event: PlayerJoinEvent) = handle(event.player, event.eventName)

    @EventHandler()
    fun onPlayerQuit(event: PlayerQuitEvent) = handle(event.player, event.eventName)

    @EventHandler()
    fun onPlayerDeath(event: PlayerDeathEvent) = handle(event.entity, event.eventName)


    private fun getWrapperLeft(): String = HyacinthHello.instance.config.get("wrapper-left")?.toString() ?: ""
    private fun getWrapperRight(): String = HyacinthHello.instance.config.get("wrapper-right")?.toString() ?: ""


    private fun handle(player: Player, eventName: String) {
        val type = when (eventName) {
            "PlayerJoinEvent" -> "join"
            "PlayerQuitEvent" -> "quit"
            "PlayerDeathEvent" -> "death"
            else -> throw RuntimeException("When on evenName caught illegal event $eventName")
        }

        var message = Storage.get(player.uniqueId.toString(), "$type.msg")

        if (message.isNullOrBlank())
            return

        if (!player.hasPermission("hyacinthhello.${type}msg"))
            return

        if (!player.hasPermission("hyacinthhello.color"))
            message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message))

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyacinthHello.instance, {
            HyacinthHello.instance.server.broadcastMessage(
                ChatColor.translateAlternateColorCodes('&', "${getWrapperLeft()}$message${getWrapperRight()}")
            )
        }, 1L)
    }

    companion object {
        fun register() = HyacinthHello.instance.server.pluginManager.registerEvents(Listener(), HyacinthHello.instance)
    }
}