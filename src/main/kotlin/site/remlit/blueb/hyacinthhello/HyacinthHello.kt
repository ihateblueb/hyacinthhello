package site.remlit.blueb.hyacinthhello

import co.aikar.commands.BukkitCommandManager
import org.bukkit.plugin.java.JavaPlugin

class HyacinthHello : JavaPlugin() {
    override fun onEnable() {
        instance = this
        commandManager = BukkitCommandManager(instance)

        config.addDefault("enabled", true)

        config.addDefault("proxy-mode", false)
        config.addDefault("proxy-redis.address", "0.0.0.0")
        config.addDefault("proxy-redis.port", 6379)
        config.addDefault("proxy-redis.channel", "hyacinthhello")
        config.addDefault("proxy-redis.user", "")
        config.addDefault("proxy-redis.pass", "")

        config.addDefault("database.type", "yaml")
        config.addDefault("database.host", "0.0.0.0")
        config.addDefault("database.port", 3306)
        config.addDefault("database.db", "hyacinthhello")
        config.addDefault("database.user", "")
        config.addDefault("database.pass", "")

        config.addDefault("economy.enabled", false)
        config.addDefault("economy.type", "vault")
        config.addDefault("economy.cost.joinmsg", 10.0)
        config.addDefault("economy.cost.leavemsg", 10.0)
        config.addDefault("economy.cost.deathmsg", 10.0)

        config.addDefault("prefix", "")
        config.addDefault("wrapper-left", "&e&o")
        config.addDefault("wrapper-right", "")
        config.addDefault("maximum-message-length", 60)
        config.addDefault("regex-filters", listOf<String>())
        config.options().copyDefaults(true)
        saveConfig()

        if (!config.getBoolean("enabled")) {
            Logger.warn("Plugin disabled by configuration, shutting down")
            instance.server.pluginManager.disablePlugin(this)
        }

        Storage.init()
        Metrics.register()
        EventListener.register()
        Commands.register()

        if (config.getBoolean("proxy-mode")) {
            Logger.info("Proxy mode enabled")
            ProxyMessenger.register()
        }

        Economy.register()

        if (instance.server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            Logger.info("Found PlaceholderAPI, registering expansion")
            Expansion().register()
        }
    }

    companion object {
        lateinit var instance: JavaPlugin
        lateinit var commandManager: BukkitCommandManager
    }
}