package site.remlit.blueb.hyacinthhello

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import site.remlit.blueb.hyacinthhello.event.HyacinthDeathMessageEvent
import site.remlit.blueb.hyacinthhello.event.HyacinthJoinMessageEvent
import site.remlit.blueb.hyacinthhello.event.HyacinthLeaveMessageEvent

class EventListener : Listener {
    private fun inProxyMode(): Boolean = HyacinthHello.instance.config.get("proxy-mode")?.toString()?.toBoolean() ?: false

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(event: PlayerJoinEvent) = handle(event.player, event.eventName, joinEvent = event)

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerQuit(event: PlayerQuitEvent) = handle(event.player, event.eventName, leaveEvent = event)

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDeath(event: PlayerDeathEvent) = handle(event.entity, event.eventName, deathEvent = event)


    private fun getWrapperLeft(): String = HyacinthHello.instance.config.get("wrapper-left")?.toString() ?: ""
    private fun getWrapperRight(): String = HyacinthHello.instance.config.get("wrapper-right")?.toString() ?: ""


    private fun handle(
        player: Player,
        eventName: String,
        joinEvent: PlayerJoinEvent? = null,
        leaveEvent: PlayerQuitEvent? = null,
        deathEvent: PlayerDeathEvent? = null
    ) {
        val type = when (eventName) {
            "PlayerJoinEvent" -> "join"
            "PlayerQuitEvent" -> "leave"
            "PlayerDeathEvent" -> "death"
            else -> throw RuntimeException("When on evenName caught illegal event $eventName")
        }

        if (inProxyMode())
            when (type) {
                "join" -> joinEvent?.joinMessage = null
                "leave" -> leaveEvent?.quitMessage = null
                "death" -> {
                    ProxyMessenger.send(listOf("vanilla_death", "${deathEvent?.deathMessage}"))
                    deathEvent?.deathMessage = null
                }
            }

        var message = Storage.get(player.uniqueId.toString(), "$type.msg")

        if (inProxyMode()) {
            handleProxyMessage(type, player, message)
            return
        }

        if (message.isNullOrBlank())
            return

        if (!player.hasPermission("hyacinthhello.${type}msg"))
            return

        if (!player.hasPermission("hyacinthhello.color"))
            message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message))

        when (type) {
            "join" -> HyacinthJoinMessageEvent(player, message!!)
            "leave" -> HyacinthLeaveMessageEvent(player, message!!)
            "death" -> HyacinthDeathMessageEvent(player, message!!)
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyacinthHello.instance, {
            HyacinthHello.instance.server.broadcastMessage(
                ChatColor.translateAlternateColorCodes('&', "${getWrapperLeft()}$message${getWrapperRight()}")
            )
        }, 1L)
    }

    fun handleProxyMessage(type: String, player: Player, message: String?) {
        var message = message

        if (!player.hasPermission("hyacinthhello.${type}msg"))
            message = null

        if (message != null && !player.hasPermission("hyacinthhello.color"))
            message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message))

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyacinthHello.instance, {
            ProxyMessenger.send(
                listOf(
                    type,
                    "${player.uniqueId},${player.name}",
                    message ?: "NULL"
                )
            )
        }, if (type != "join") 0L else 1L)
    }

    companion object {
        fun register() = HyacinthHello.instance.server.pluginManager.registerEvents(EventListener(), HyacinthHello.instance)
    }
}