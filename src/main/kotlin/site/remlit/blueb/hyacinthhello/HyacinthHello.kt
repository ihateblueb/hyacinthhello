package site.remlit.blueb.hyacinthhello

import co.aikar.commands.BukkitCommandManager
import org.bukkit.plugin.java.JavaPlugin

class HyacinthHello : JavaPlugin() {
    override fun onEnable() {
        instance = this
        commandManager = BukkitCommandManager(instance)

        config.addDefault("enabled", true)
        config.addDefault("prefix", "")
        config.addDefault("wrapper-left", "&e&o")
        config.addDefault("wrapper-right", "")
        config.addDefault("maximum-message-length", 60)
        config.addDefault("regex-filters", listOf<String>())
        config.options().copyDefaults(true)
        saveConfig()

        Storage.init()
        Metrics.register()

        if (!config.getBoolean("enabled")) {
            Logger.warn("Plugin disabled by configuration, shutting down.")
            instance.server.pluginManager.disablePlugin(this)
        }

        if (instance.server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            Logger.info("Found PlaceholderAPI, registering expansion.")
            Expansion().register()
        }

        Listener.register()
        Commands.register()
    }
    override fun onDisable() {}

    companion object {
        lateinit var instance: JavaPlugin
        lateinit var commandManager: BukkitCommandManager
    }
}