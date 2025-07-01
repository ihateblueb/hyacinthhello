package site.remlit.blueb.hyacinthhello

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class Listener : Listener {
    @EventHandler()
    fun onPlayerJoin(event: PlayerJoinEvent) {

    }

    @EventHandler()
    fun onPlayerQuit(event: PlayerQuitEvent) {

    }

    @EventHandler()
    fun onPlayerDeath(event: PlayerDeathEvent) {

    }

    companion object {
        fun register() = HyacinthHello.instance.server.pluginManager.registerEvents(Listener(), HyacinthHello.instance)
    }
}