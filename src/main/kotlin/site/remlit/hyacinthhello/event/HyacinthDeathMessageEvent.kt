package site.remlit.hyacinthhello.event

import org.bukkit.entity.Player

class HyacinthDeathMessageEvent(player: Player, message: String) : HyacinthMessageEvent(player, message)