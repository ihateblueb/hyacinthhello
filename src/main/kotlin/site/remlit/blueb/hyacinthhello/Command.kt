package site.remlit.blueb.hyacinthhello

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import org.bukkit.command.CommandSender

@Suppress("Unused")
@CommandAlias("hyacinthhello|hyacinth|hh")
class Command {
    @Default
    fun default(sender: CommandSender) {

    }

    @Subcommand("joinmsg")
    @CommandAlias("joinmsg")
    fun joinmsg(sender: CommandSender) {

    }
}