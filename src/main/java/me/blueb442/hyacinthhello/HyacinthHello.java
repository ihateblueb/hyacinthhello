package me.blueb442.hyacinthhello;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class HyacinthHello extends JavaPlugin implements Listener, CommandExecutor {
    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(this, this);

        this.getCommand("joinmsg").setExecutor(new CommandJoinmsg());
        this.getCommand("leavemsg").setExecutor(new CommandLeavemsg());

        // Begin forming default config
        config.addDefault("enabled", true);
        config.addDefault("prefix", "§dHyacinthHello §8»§r");
        config.addDefault("hello-wrapper-left", "§6»§e ");
        config.addDefault("hello-wrapper-right", " §6«");
        config.addDefault("maximum-message-length", 60);

        config.options().copyDefaults(true);
        saveConfig();

        if (config.getBoolean("enabled")) {
            return;
        } else {
            Bukkit.getLogger().info("Disabled by config.yml! Set enabled to true in config.yml to enable HyacinthHello.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getLogger().info("Saving data and shutting down. Goodbye!");

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        UUID joinerUUID = p.getUniqueId();
        File userdata = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello")).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        //When the player file is created for the first time...
        if (!f.exists()) {
            try {

                playerData.createSection("join");
                playerData.set("join.msg", "");

                playerData.save(f);

                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + "(" + p.getName() + ")");
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");

        // haha iconic user-made pun goes here
        String message = playerData.getString("join.msg");

        if (Objects.equals(playerData.getString("join.msg"), "")) {
            Bukkit.getLogger().info("No hello found for " + joinerUUID);
        } else if (Objects.equals(playerData.getString("join.msg"), null)) {
            Bukkit.getLogger().info("No hello found for " + joinerUUID);
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                Bukkit.broadcastMessage(hwl + message + hwr);
                Bukkit.getLogger().info("Hello sent for " + joinerUUID);
            },  2L); // delay for # of ticks, so it appears UNDER {user} joined.
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        UUID quitterUUID = p.getUniqueId();
        File userdata = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello")).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + quitterUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        //When the player file is created for the first time...
        if (!f.exists()) {
            try {

                playerData.createSection("leave");
                playerData.set("leave.msg", "");

                playerData.save(f);

                Bukkit.getLogger().info("Created a data file for player " + quitterUUID + "(" + p.getName() + ")");
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");

        // haha iconic user-made pun goes here
        String message = playerData.getString("leave.msg");

        if (Objects.equals(playerData.getString("leave.msg"), "")) {
            Bukkit.getLogger().info("No goodbye found for " + quitterUUID);
        } else if (Objects.equals(playerData.getString("leave.msg"), null)) {
            Bukkit.getLogger().info("No goodbye found for " + quitterUUID);
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                Bukkit.broadcastMessage(hwl + message + hwr);
                Bukkit.getLogger().info("Goodbye sent for " + quitterUUID);
            },  2L); // delay for # of ticks, so it appears UNDER {user} left.
        }

    }

}
