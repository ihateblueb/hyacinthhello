package site.remlit.blueb.hyacinthhello;

import org.bstats.bukkit.Metrics;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class HyacinthHelloOld extends JavaPlugin implements Listener, CommandExecutor {
    FileConfiguration config = this.getConfig();

    public HyacinthHelloOld() {
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("joinmsg")).setExecutor(new CommandJoinmsg());
        Objects.requireNonNull(this.getCommand("leavemsg")).setExecutor(new CommandLeavemsg());
        Objects.requireNonNull(this.getCommand("deathmsg")).setExecutor(new CommandDeathmsg());
        Objects.requireNonNull(this.getCommand("hyacinthhello")).setExecutor(new CommandHyacinthHello());

        new Metrics(this, 16278);

        this.config.addDefault("enabled", true);
        this.config.addDefault("prefix", "§dHyacinthHello §8»§r");
        this.config.addDefault("hello-wrapper-left", "§6»§e ");
        this.config.addDefault("hello-wrapper-right", " §6«");
        this.config.addDefault("maximum-message-length", 60);
        this.config.options().copyDefaults(true);
        this.saveConfig();
        if (!this.config.getBoolean("enabled")) {
            Bukkit.getLogger().info("[HyacinthHello] Disabled by config.yml! Check the enabled field to reinstall.");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        // PlaceholderAPI hook
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HyacinthHelloExpansion().register();
            Bukkit.getLogger().info("[HyacinthHello] Hooked into PlaceholderAPI. See placeholders on wiki.");
        }

    }

    public void onDisable() {
        Bukkit.getLogger().info("[HyacinthHello] Shutting down. Goodbye!");
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID joinerUUID = p.getUniqueId();
        File userdata = new File(((Plugin)Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("join");
                playerData.set("join.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("[HyacinthHello] Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[HyacinthHello] Could not generate or save the configuration! Thrown on onPlayerJoin event. Please report the above errors to the author.");
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");
        String message = playerData.getString("join.msg");
        if (Objects.equals(playerData.getString("join.msg"), "")) {
            Bukkit.getLogger().info("[HyacinthHello] No hello found for " + joinerUUID + " (" + p.getName() + ")");
        } else if (Objects.equals(playerData.getString("join.msg"), (Object)null)) {
            Bukkit.getLogger().info("[HyacinthHello] No hello found for " + joinerUUID + " (" + p.getName() + ")");
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (p.hasPermission("hyacinthhello.joinmessage")) {
                    if (p.hasPermission("hyacinthhello.usecolor")) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', hwl + message + hwr));
                        Bukkit.getLogger().info("[HyacinthHello] Hello sent for " + joinerUUID + " (" + p.getName() + ")");
                    } else {
                        Bukkit.broadcastMessage(hwl + message + hwr);
                        Bukkit.getLogger().info("[HyacinthHello] Hello sent for " + joinerUUID + " (" + p.getName() + ")");
                    }
                }
            }, 2L);
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        UUID quitterUUID = p.getUniqueId();
        File userdata = new File(((Plugin)Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + quitterUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("leave");
                playerData.set("leave.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("[HyacinthHello] Created a data file for player " + quitterUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[HyacinthHello] Could not generate or save the configuration! Thrown on onPlayerQuit event. Please report the above errors to the author.");
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");
        String message = playerData.getString("leave.msg");
        if (Objects.equals(playerData.getString("leave.msg"), "")) {
            Bukkit.getLogger().info("[HyacinthHello] No goodbye found for " + quitterUUID + " (" + p.getName() + ")");
        } else if (Objects.equals(playerData.getString("leave.msg"), (Object)null)) {
            Bukkit.getLogger().info("[HyacinthHello] No goodbye found for " + quitterUUID + " (" + p.getName() + ")");
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (p.hasPermission("hyacinthhello.leavemessage")) {
                    if (p.hasPermission("hyacinthhello.usecolor")) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', hwl + message + hwr));
                        Bukkit.getLogger().info("[HyacinthHello] Goodbye sent for " + quitterUUID + " (" + p.getName() + ")");
                    } else {
                        Bukkit.broadcastMessage(hwl + message + hwr);
                        Bukkit.getLogger().info("[HyacinthHello] Goodbye sent for " + quitterUUID + " (" + p.getName() + ")");
                    }
                }
            }, 0L);
        }

    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player p = event.getEntity().getPlayer();
        // prevents any errors when player is not defined
        assert p != null;
        UUID deadUUID = p.getUniqueId();
        File userdata = new File(((Plugin)Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + deadUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("death");
                playerData.set("death.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("[HyacinthHello] Created a data file for player " + deadUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[HyacinthHello] Could not generate or save the configuration! Thrown on onPlayerDeath event. Please report the above errors to the author.");
            }
        }

        String hwl = this.getConfig().getString("hello-wrapper-left");
        String hwr = this.getConfig().getString("hello-wrapper-right");
        String message = playerData.getString("death.msg");
        if (Objects.equals(playerData.getString("death.msg"), "")) {
            Bukkit.getLogger().info("[HyacinthHello] No death found for " + deadUUID + " (" + p.getName() + ")");
        } else if (Objects.equals(playerData.getString("death.msg"), (Object)null)) {
            Bukkit.getLogger().info("[HyacinthHello] No death found for " + deadUUID + " (" + p.getName() + ")");
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (p.hasPermission("hyacinthhello.deathmessage")) {
                    if (p.hasPermission("hyacinthhello.usecolor")) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', hwl + message + hwr));
                        Bukkit.getLogger().info("[HyacinthHello] Death sent for " + deadUUID + " (" + p.getName() + ")");
                    } else {
                        Bukkit.broadcastMessage(hwl + message + hwr);
                        Bukkit.getLogger().info("[HyacinthHello] Death sent for " + deadUUID + " (" + p.getName() + ")");
                    }
                }
            }, 2L);
        }

    }
}
