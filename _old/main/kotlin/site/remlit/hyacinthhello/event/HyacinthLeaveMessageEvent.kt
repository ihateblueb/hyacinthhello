package site.remlit.hyacinthhello.event

import org.bukkit.entity.Player

class HyacinthLeaveMessageEvent(player: Player, message: String) : HyacinthMessageEvent(player, message)