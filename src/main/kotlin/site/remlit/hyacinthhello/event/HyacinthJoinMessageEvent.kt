package site.remlit.hyacinthhello.event

import org.bukkit.entity.Player

class HyacinthJoinMessageEvent(player: Player, message: String) : HyacinthMessageEvent(player, message)