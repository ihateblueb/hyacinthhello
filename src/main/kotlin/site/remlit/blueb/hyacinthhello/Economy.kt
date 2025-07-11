package site.remlit.blueb.hyacinthhello

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.black_ixx.playerpoints.PlayerPoints
import org.black_ixx.playerpoints.PlayerPointsAPI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import site.remlit.blueb.hyacinthhello.exception.EconomyException
import site.remlit.blueb.hyacinthhello.util.getPrefix

class Economy {
    companion object {
        private var enabled = HyacinthHello.instance.config.get("economy.enabled")?.toString()?.toBoolean() ?: false
        private val type = HyacinthHello.instance.config.get("economy.type")?.toString() ?: "vault"

        private lateinit var pointsApi: PlayerPointsAPI
        private lateinit var vaultEconomy: Economy

        fun register() {
            if (!enabled) return

            when (type) {
                "vault" -> {
                    if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                        enabled = false
                        Logger.info("Vault wasn't enabled, disabling economy support")
                        return
                    }

                    val rsp = HyacinthHello.instance.server.servicesManager.getRegistration(Economy::class.java)
                    if (rsp == null) {
                        enabled = false
                        Logger.info("No Vault economy provider found, disabling economy support")
                        return
                    }

                    vaultEconomy = rsp.provider
                    Logger.info("Hooked into Vault")
                }
                "playerpoints" -> {
                    if (!Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
                        enabled = false
                        Logger.info("PlayerPoints wasn't enabled, disabling economy support")
                    }

                    pointsApi = PlayerPoints.getInstance().api
                    Logger.info("Hooked into PlayerPoints")
                }
                else -> throw EconomyException("Illegal economy type $type")
            }
        }

        fun charge(player: Player, messageType: MessageType): Boolean? {
            when (type) {
                "vault" -> {
                    if (!enabled) return null

                    val cost = HyacinthHello.instance.config.get("economy.cost.$messageType")?.toString()?.toInt()?.toDouble() ?: 10.0
                    val result = vaultEconomy.withdrawPlayer(
                        player,
                        cost
                    ).type == EconomyResponse.ResponseType.SUCCESS

                    if (result) {
                        player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "${getPrefix()}Charged ${vaultEconomy.format(cost)} to change your message")
                        )
                    } else {
                        player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "${getPrefix()}You cannot afford this, changing your message costs ${vaultEconomy.format(cost)}")
                        )
                    }
                    return result
                }
                "playerpoints" -> {
                    if (!enabled) return null

                    val cost = HyacinthHello.instance.config.get("economy.cost.$messageType")?.toString()?.toInt() ?: 10
                    val result = pointsApi.take(player.uniqueId, cost)

                    if (result) {
                        player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "${getPrefix()}Charged $cost points to change your message")
                        )
                    } else {
                        player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "${getPrefix()}You cannot afford this, changing your message costs $cost points")
                        )
                    }
                    return result
                }
                else -> throw EconomyException("Illegal economy type $type")
            }
        }
    }
}