package me.blueb442.hyacinthhello;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHyacinthHello implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        String pfx = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello").getConfig().getString("prefix")));
        if (args.length == 1) {
            if (Objects.equals(args[0], "reload")) {
                Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello").reloadConfig();
                p.sendMessage(pfx + " HyacinthHello configuration reloaded.");
            }
        } else {
            p.sendMessage(pfx + " Incorrect usage! /" + command.getName() + " reload");
        }
        return true;
    }
}
