package site.remlit.blueb.hyacinthhello

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class Expansion : PlaceholderExpansion() {
    override fun getIdentifier(): String = "hyacinthhello"
    override fun getAuthor(): String = "blueb"
    override fun getVersion(): String = "2.0.0"

    override fun onPlaceholderRequest(player: Player?, params: String): String? {
        println(player)
        println(params)
        val split = params.split("_")
        return null
    }
}