package site.remlit.blueb.hyacinthhello

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
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


    private fun handle(type: String, sender: CommandSender, args: Array<String>?) {
        val player = sender as Player

        if (args.isNullOrEmpty()) {
            sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', "${getPrefix()}No message passed")
            )
            return
        }

        val sentence = args.joinToString(" ")

        if (sentence.length > getMaxLength()) {
            sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', "${getPrefix()}Message too long, max length is ${getMaxLength()} characters")
            )
            return
        }

        Storage.set(player.uniqueId.toString(), "$type.msg", sentence)
        sender.sendMessage(
            ChatColor.translateAlternateColorCodes('&', "${getPrefix()}Set $type message to $sentence&r.")
        )
        return
    }

    companion object {
        fun register() = HyacinthHello.commandManager.registerCommand(Commands())
    }
}