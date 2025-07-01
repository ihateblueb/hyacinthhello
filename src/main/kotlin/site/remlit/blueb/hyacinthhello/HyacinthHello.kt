package site.remlit.blueb.hyacinthhello

import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin

class HyacinthHello : JavaPlugin() {
    override fun onEnable() {
        instance = this
        Metrics(this, 16278)

        config.addDefault("enabled", true)
        config.addDefault("prefix", "§dHyacinthHello §8»§r")
        config.addDefault("hello-wrapper-left", "§6»§e ")
        config.addDefault("hello-wrapper-right", " §6«")
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
    }
    override fun onDisable() {}

    companion object {
        lateinit var instance: JavaPlugin
    }
}