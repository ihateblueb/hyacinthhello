package site.remlit.blueb.hyacinthhello

import co.aikar.commands.BukkitCommandManager
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

class HyacinthHello : JavaPlugin() {
    override fun onEnable() {
        instance = this
        commandManager = BukkitCommandManager(instance)
        Metrics(this, 16278)

        config.addDefault("enabled", true)
        config.addDefault("prefix", "")
        config.addDefault("wrapper-left", "&e&o")
        config.addDefault("wrapper-right", "")
        config.addDefault("maximum-message-length", 60)
        config.options().copyDefaults(true)
        saveConfig()

        if (!config.getBoolean("enabled")) {
            Logger.warn("Plugin disabled by configuration, shutting down.")
            instance.server.pluginManager.disablePlugin(this)
        }

        if (instance.server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            Logger.info("Found PlaceholderAPI, registering expansion.")
            Expansion().register()
        }

        Storage.init()
        Listener.register()
        Commands.register()
    }
    override fun onDisable() {}

    companion object {
        lateinit var instance: JavaPlugin
        lateinit var commandManager: BukkitCommandManager
    }
}