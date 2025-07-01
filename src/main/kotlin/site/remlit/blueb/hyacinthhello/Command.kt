package site.remlit.blueb.hyacinthhello

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender

@Suppress("Unused")
@CommandAlias("hyacinthhello|hyacinth|hh")
@CommandPermission("hyacinthhello.use")
class Command {
    @Default
    fun default(sender: CommandSender) {

    }

    @Subcommand("joinmsg")
    @CommandAlias("joinmsg")
    @CommandPermission("hyacinthhello.joinmsg")
    fun joinMsg(sender: CommandSender) {

    }

    @Subcommand("leavemsg")
    @CommandAlias("leavemsg|quitmsg")
    @CommandPermission("hyacinthhello.leavemsg")
    fun leaveMsg(sender: CommandSender) {

    }

    @Subcommand("deathmsg")
    @CommandAlias("deathmsg")
    @CommandPermission("hyacinthhello.deathmsg")
    fun deathMsg(sender: CommandSender) {

    }
}