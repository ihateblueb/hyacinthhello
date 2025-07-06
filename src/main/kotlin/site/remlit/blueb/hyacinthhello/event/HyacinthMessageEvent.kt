package site.remlit.blueb.hyacinthhello.event

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.jetbrains.annotations.ApiStatus

@ApiStatus.OverrideOnly
@Suppress("unused")
open class HyacinthMessageEvent(
    val player: Player,
    val message: String
) : Event() {
    override fun getHandlers(): HandlerList = HANDLER_LIST
    companion object {
        @JvmStatic
        @Suppress("Unused")
        fun getHandlerList(): HandlerList = HANDLER_LIST
        val HANDLER_LIST: HandlerList = HandlerList()
    }
}