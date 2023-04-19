package me.blueb442.hyacinthhello;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class HyacinthHelloExpansion extends PlaceholderExpansion {

    // get methods, offline are below this one
    public String getJoinMessage(Player p) {
        UUID joinerUUID = p.getUniqueId();
        File userdata = new File(((Plugin) Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("join");
                playerData.set("join.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("Could not generate or save the configuration! Thrown on getJoinMessage event.");
                Bukkit.getLogger().warning("Please report the above errors to the author.");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
            }
        }
        String message = playerData.getString("join.msg");
        if (Objects.equals(playerData.getString("join.msg"), "")) {
            Bukkit.getLogger().info("[Called by getJoinMessage] No hello found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        } else if (Objects.equals(playerData.getString("join.msg"), (Object) null)) {
            Bukkit.getLogger().info("[Called by getJoinMessage] No hello found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        }
        return message;
    }
    public String getLeaveMessage(Player p) {
        UUID joinerUUID = p.getUniqueId();
        File userdata = new File(((Plugin) Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("leave");
                playerData.set("leave.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("Could not generate or save the configuration! Thrown on getLeaveMessage event.");
                Bukkit.getLogger().warning("Please report the above errors to the author.");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
            }
        }
        String message = playerData.getString("leave.msg");
        if (Objects.equals(playerData.getString("leave.msg"), "")) {
            Bukkit.getLogger().info("[Called by getLeaveMessage] No goodbye found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        } else if (Objects.equals(playerData.getString("leave.msg"), (Object) null)) {
            Bukkit.getLogger().info("[Called by getLeaveMessage] No goodbye found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        }
        return message;
    }
    public String getDeathMessage(Player p) {
        UUID joinerUUID = p.getUniqueId();
        File userdata = new File(((Plugin) Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("death");
                playerData.set("death.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("Could not generate or save the configuration! Thrown on getDeathMessage event.");
                Bukkit.getLogger().warning("Please report the above errors to the author.");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
            }
        }
        String message = playerData.getString("death.msg");
        if (Objects.equals(playerData.getString("death.msg"), "")) {
            Bukkit.getLogger().info("[Called by getDeathMessage] No death found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        } else if (Objects.equals(playerData.getString("leave.msg"), (Object) null)) {
            Bukkit.getLogger().info("[Called by getDeathMessage] No death found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        }
        return message;
    }

    public String getOfflineJoinMessage(String pname) {
        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(pname);
        UUID joinerUUID = Bukkit.getServer().getOfflinePlayer(pname).getUniqueId();
        File userdata = new File(((Plugin) Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("join");
                playerData.set("join.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("Could not generate or save the configuration! Thrown on getJoinMessage event.");
                Bukkit.getLogger().warning("Please report the above errors to the author.");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
            }
        }
        String message = playerData.getString("join.msg");
        if (Objects.equals(playerData.getString("join.msg"), "")) {
            Bukkit.getLogger().info("[Called by getOfflineJoinMessage] No hello found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        } else if (Objects.equals(playerData.getString("join.msg"), (Object) null)) {
            Bukkit.getLogger().info("[Called by getOfflineJoinMessage] No hello found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        }
        return message;
    }

    public String getOfflineLeaveMessage(String pname) {
        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(pname);
        UUID joinerUUID = Bukkit.getServer().getOfflinePlayer(pname).getUniqueId();
        File userdata = new File(((Plugin) Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("leave");
                playerData.set("leave.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("Could not generate or save the configuration! Thrown on getLeaveMessage event.");
                Bukkit.getLogger().warning("Please report the above errors to the author.");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
            }
        }
        String message = playerData.getString("leave.msg");
        if (Objects.equals(playerData.getString("leave.msg"), "")) {
            Bukkit.getLogger().info("[Called by getOfflineLeaveMessage] No goodbye found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        } else if (Objects.equals(playerData.getString("leave.msg"), (Object) null)) {
            Bukkit.getLogger().info("[Called by getOfflineLeaveMessage] No goodbye found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        }
        return message;
    }
    public String getOfflineDeathMessage(String pname) {
        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(pname);
        UUID joinerUUID = Bukkit.getServer().getOfflinePlayer(pname).getUniqueId();
        File userdata = new File(((Plugin) Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("HyacinthHello"))).getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userdata, File.separator + joinerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
        if (!f.exists()) {
            try {
                playerData.createSection("death");
                playerData.set("death.msg", "");
                playerData.save(f);
                Bukkit.getLogger().info("Created a data file for player " + joinerUUID + " (" + p.getName() + ")");
            } catch (IOException var10) {
                var10.printStackTrace();
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("Could not generate or save the configuration! Thrown on getDeathMessage event.");
                Bukkit.getLogger().warning("Please report the above errors to the author.");
                Bukkit.getLogger().warning(" ");
                Bukkit.getLogger().warning("[===---------* Hyacinth Hello *---------==]");
            }
        }
        String message = playerData.getString("death.msg");
        if (Objects.equals(playerData.getString("death.msg"), "")) {
            Bukkit.getLogger().info("[Called by getOfflineDeathMessage] No death found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        } else if (Objects.equals(playerData.getString("leave.msg"), (Object) null)) {
            Bukkit.getLogger().info("[Called by getOfflineDeathMessage] No death found for " + joinerUUID + " (" + p.getName() + ")");
            message = "";
        }
        return message;
    }

    @Override
    public String getAuthor() {
        return "blueb";
    }

    @Override
    public String getIdentifier() {
        return "hyacinthhello";
    }

    @Override
    public String getVersion() {
        return "0.2.0 (1.5.3)";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (params.equalsIgnoreCase("join")) {
            if(player.isOnline()) {
                return getJoinMessage(player);
            } else {
                return "User offline.";
            }
        }

        if (params.equalsIgnoreCase("leave")) {
            if(player.isOnline()) {
                return getLeaveMessage(player);
            } else {
                return "User offline.";
            }
        }

        if (params.equalsIgnoreCase("death")) {
            if(player.isOnline()) {
                return getDeathMessage(player);
            } else {
                return "User offline.";
            }
        }

        if (params.startsWith("join_")) {
            String playerName = params.replace("join_","");
            String cleanPlayerName = playerName.replaceAll("\\\\", "");
            String joinmessage = getOfflineJoinMessage(cleanPlayerName);
            if (!Objects.equals(joinmessage, "")) {
                return joinmessage;
            } else {
                return "";
            }
        }

        if (params.startsWith("leave_")) {
            String playerName = params.replace("leave_","");
            String cleanPlayerName = playerName.replaceAll("\\\\", "");
            String leavemessage = getOfflineLeaveMessage(cleanPlayerName);
            if (!Objects.equals(leavemessage, "")) {
                return leavemessage;
            } else {
                return "";
            }
        }

        if (params.startsWith("death_")) {
            String playerName = params.replace("death_","");
            String cleanPlayerName = playerName.replaceAll("\\\\", "");
            String deathmessage = getOfflineDeathMessage(cleanPlayerName);
            if (!Objects.equals(deathmessage, "")) {
                return deathmessage;
            } else {
                return "";
            }
        }

        return null;
    }


}
