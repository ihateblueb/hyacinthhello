package site.remlit.blueb.hyacinthhello.event

import org.bukkit.entity.Player

class HyacinthLeaveMessageEvent(player: Player, message: String) : HyacinthMessageEvent(player, message)