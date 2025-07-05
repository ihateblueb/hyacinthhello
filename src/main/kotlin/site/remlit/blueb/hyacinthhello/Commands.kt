package site.remlit.blueb.hyacinthhello

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Suppress("Unused")
@CommandAlias("hyacinthhello|hyacinth|hh")
@CommandPermission("hyacinthhello.use")
class Commands : BaseCommand() {
    @Default
    fun default(sender: CommandSender) {
        sender.sendMessage("Running HyacinthHello version ${HyacinthHello.instance.description.version}")
    }

    private fun getPrefix(): String {
        val prefix = HyacinthHello.instance.config.get("prefix")?.toString() ?: ""
        return if (prefix.isEmpty()) ""
        else "$prefix "
    }
    private fun getMaxLength() = HyacinthHello.instance.config.get("maximum-message-length")?.toString()?.toInt() ?: 60


    @Subcommand("joinmsg")
    @CommandAlias("joinmsg")
    @CommandPermission("hyacinthhello.joinmsg")
    fun joinMsg(sender: CommandSender, args: Array<String>?) = handle("join", sender, args)

    @Subcommand("leavemsg")
    @CommandAlias("leavemsg|quitmsg")
    @CommandPermission("hyacinthhello.leavemsg")
    fun leaveMsg(sender: CommandSender, args: Array<String>?) = handle("leave", sender, args)

    @Subcommand("deathmsg")
    @CommandAlias("deathmsg")
    @CommandPermission("hyacinthhello.deathmsg")
    fun deathMsg(sender: CommandSender, args: Array<String>?) = handle("death", sender, args)


    @Subcommand("mod joinmsg")
    @Syntax("[player] [message]")
    @CommandPermission("hyacinthhello.mod")
    fun modJoinMsg(sender: CommandSender, args: Array<String>?) = handleMod("join", sender, args)

    @Subcommand("mod leavemsg")
    @Syntax("[player] [message]")
    @CommandPermission("hyacinthhello.mod")
    fun modLeaveMsg(sender: CommandSender, args: Array<String>?) = handleMod("leave", sender, args)

    @Subcommand("mod deathmsg")
    @Syntax("[player] [message]")
    @CommandPermission("hyacinthhello.mod")
    fun modDeathMsg(sender: CommandSender, args: Array<String>?) = handleMod("death", sender, args)


    private fun handleMod(type: String, sender: CommandSender, args: Array<String>?) {
        val id = args?.getOrNull(0)
        if (id == null) {
            sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', "${getPrefix()}Please specify a player's username or UUID")
            )
            return
        }
        return handle(type, sender, args.filter { it != id }.toTypedArray(), id)
    }

    private fun handle(type: String, sender: CommandSender, args: Array<String>?, username: String? = null) {
        val player = sender as Player
        val otherPlayer = HyacinthHello.instance.server.getPlayer(username ?: "")
        if (username != null && otherPlayer == null) {
            sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', "${getPrefix()}Player not found")
            )
            return
        }

        var notSelf = false
        if (username != null)
            notSelf = true

        val msgPlayer = if (notSelf) otherPlayer!! else player

        val sentence = args?.joinToString(" ") ?: ""
        if (sentence.length > getMaxLength()) {
            sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', "${getPrefix()}Message too long, max length is ${getMaxLength()} characters")
            )
            return
        }

        val filters = HyacinthHello.instance.config.getList("regex-filters")
        if (filters != null && filters.isNotEmpty()) {
            for (filter in filters) {
                val string = filter.toString()
                if (string.isNotBlank() && sentence.matches(Regex(string))) {
                    sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', "${getPrefix()}This message isn't allowed")
                    )
                    return
                }
            }
        }

        Storage.set(msgPlayer.uniqueId.toString(), "$type.msg", sentence)
        sender.sendMessage(
            ChatColor.translateAlternateColorCodes('&',
                if (sentence.isBlank()) "${getPrefix()}Cleared $type message${if (notSelf) " for " + msgPlayer.name else ""}"
                else "${getPrefix()}Set $type message to $sentence${if (notSelf) " for " + msgPlayer.name else ""}"
            )
        )
        return
    }

    companion object {
        fun register() = HyacinthHello.commandManager.registerCommand(Commands())
    }
}