package site.remlit.blueb.hyacinthhello

import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
import org.bstats.charts.SingleLineChart
import kotlin.io.path.name

class Metrics {
    companion object {
        lateinit var metrics: Metrics

        fun register() {
            metrics = Metrics(HyacinthHello.instance, 16278)

            metrics.addCustomChart(SimplePie("character_limit") { HyacinthHello.instance.config.getString("maximum-message-length", "60") })
            metrics.addCustomChart(SimplePie("proxy_mode") { HyacinthHello.instance.config.getString("proxy-mode", "false") })
            metrics.addCustomChart(SimplePie("database_type") { HyacinthHello.instance.config.getString("database.type", "yaml") })
            metrics.addCustomChart(SimplePie("economy_enabled") { HyacinthHello.instance.config.getString("economy.enabled", "false") })
            metrics.addCustomChart(SimplePie("economy_type") {
                if (HyacinthHello.instance.config.getBoolean("economy.enabled", false))
                    HyacinthHello.instance.config.getString("economy.type", "vault")
                else "none"
            })

            metrics.addCustomChart(SingleLineChart("players_with_join_message_set") { collectMessageCount("join") })
            metrics.addCustomChart(SingleLineChart("players_with_leave_message_set") { collectMessageCount("leave") })
            metrics.addCustomChart(SingleLineChart("players_with_death_message_set") { collectMessageCount("death") })
        }

        fun collectMessageCount(type: String): Int {
            var count = 0

            val players = Storage.listPlayers()
            for (player in players) {
                if (!Storage.get(player, "$type.msg").isNullOrBlank()) count++
            }

            return count
        }
    }
}