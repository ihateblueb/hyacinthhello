package site.remlit.blueb.hyacinthhello

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class Storage {
    companion object {
        private val root = HyacinthHello.instance.dataFolder

        fun init() {
            Files.createDirectories(Path("$root/PlayerDatabase"))
        }

        /**
         * @param player UUID of player as string
         * */
        fun get(player: String, key: String): String? {
            val file = File(Path("$root/PlayerDatabase/$player.yml").toAbsolutePath().toString())
            file.mkdirs()
            val playerData = YamlConfiguration.loadConfiguration(file)
            return try {
                playerData.getString(key)
            } catch (e: Exception) { null }
        }

        /**
         * @param player UUID of player as string
         * */
        fun set(player: String, key: String, value: String) {
            val file = File(Path("$root/PlayerDatabase/$player.yml").toAbsolutePath().toString())
            val playerData = YamlConfiguration.loadConfiguration(file)
            return try {
                playerData.set(key, value)
                playerData.save(file)
            } catch (e: Exception) { }
        }
    }
}