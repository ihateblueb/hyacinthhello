package site.remlit.hyacinthhello

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.configuration.file.YamlConfiguration
import site.remlit.hyacinthhello.exception.StorageException
import java.io.File
import java.nio.file.Files
import java.sql.Connection
import kotlin.io.path.Path

class Storage {
    companion object {
        private val root = HyacinthHello.instance.dataFolder

        private val type = HyacinthHello.instance.config.get("database.type")?.toString() ?: "yaml"
        private val host = HyacinthHello.instance.config.get("database.host")?.toString() ?: "0.0.0.0"
        private val port = HyacinthHello.instance.config.get("database.port")?.toString()
        private val db = HyacinthHello.instance.config.get("database.db")?.toString() ?: "hyacinthhello"
        private val user = HyacinthHello.instance.config.get("database.user")?.toString()
        private val pass = HyacinthHello.instance.config.get("database.pass")?.toString()

        private val isMySQL = (type == "mysql")

        lateinit var connection: Connection

        fun init() =
            when (type) {
                "yaml" -> {
                    Files.createDirectories(Path("$root/PlayerDatabase"))
                    HyacinthHello.instance.logger.info("Initialized YAML storage")
                }
                "mysql" -> {
                    Class.forName("com.mysql.cj.jdbc.Driver")

                    val hikariConfig = HikariConfig()
                    hikariConfig.jdbcUrl = "jdbc:mysql://${host}:${port ?: 3306}/${db}"
                    if (user != null) hikariConfig.username = user
                    if (pass != null) hikariConfig.password = pass

                    try {
                        val hikariDs = HikariDataSource(hikariConfig)
                        connection = hikariDs.connection
                        sqlMigrations()
                        HyacinthHello.instance.logger.info("Initialized MySQL storage")
                    } catch (e: Exception) {
                        throw StorageException("Failed to initialize HikariDataSource: $e")
                    }
                }
                "postgres" -> {
                    Class.forName("org.postgresql.Driver")

                    val hikariConfig = HikariConfig()
                    hikariConfig.jdbcUrl = "jdbc:postgresql://${host}:${port ?: 5432}/${db}"
                    if (user != null) hikariConfig.username = user
                    if (pass != null) hikariConfig.password = pass

                    try {
                        val hikariDs = HikariDataSource(hikariConfig)
                        connection = hikariDs.connection
                        sqlMigrations()
                        HyacinthHello.instance.logger.info("Initialized Postgres storage")
                    } catch (e: Exception) {
                        throw StorageException("Failed to initialize HikariDataSource: $e")
                    }
                }
                else -> throw StorageException("Illegal database type $type")
            }

        fun sqlMigrations() {
            connection.createStatement().use { statement ->
                statement.execute("create table if not exists player (" +
                        "uuid ${if (isMySQL) "text" else "uuid"}," +
                        "joinmsg text NULL," +
                        "leavemsg text NULL," +
                        "deathmsg text NULL" +
                        ")")
            }
        }

        private fun sqlPlayerColumnExists(uuid: String): Boolean {
            connection.createStatement().use { statement ->
                statement.executeQuery("select * from player where uuid = '$uuid'").use { rs ->
                    while (rs.next())
                        return true
                }
            }
            return false
        }

        private fun sqlCreatePlayerColumn(uuid: String) {
            connection.createStatement().use { statement ->
                statement.execute("insert into player values ('$uuid', null, null, null)")
            }
        }

        fun listPlayers(): List<String> =
            when (type) {
                "yaml" -> {
                    val list = mutableListOf<String>()
                    for (path in Files.list(Path("$root/PlayerDatabase"))) {
                        list.add(path.fileName.toString().removeSuffix(".yml"))
                    }
                    list.toList()
                }
                "mysql", "postgres" -> {
                    val list = mutableListOf<String>()
                    connection.createStatement().use { statement ->
                        statement.executeQuery("select * from player").use { rs ->
                            while (rs.next()) {
                                try {
                                    list.add(rs.getString("uuid"))
                                } catch (e: Exception) {}
                            }
                        }
                    }
                    list.toList()
                }
                else -> throw StorageException("Illegal database type $type")
            }

        /**
         * @param player UUID of player as string
         * */
        fun get(player: String, key: String): String? =
            when (type) {
                "yaml" -> {
                    val file = File(Path("$root/PlayerDatabase/$player.yml").toString())
                    if (!file.exists()) file.createNewFile()
                    val playerData = YamlConfiguration.loadConfiguration(file)
                    try {
                        playerData.getString(key)
                    } catch (e: Exception) { null }
                }
                "mysql", "postgres" -> {
                    val key = key.replace(".", "")
                    var response: String? = null
                    connection.createStatement().use { statement ->
                        statement.executeQuery("select * from player where uuid = '$player' limit 1").use { rs ->
                            while (rs.next()) {
                                response = try {
                                    rs.getString(key)
                                } catch (e: Exception) { null }
                            }
                        }
                    }
                    response
                }
                else -> throw StorageException("Illegal database type $type")
            }

        /**
         * @param player UUID of player as string
         * */
        fun set(player: String, key: String, value: String) =
            when (type) {
                "yaml" -> {
                    val file = File(Path("$root/PlayerDatabase/$player.yml").toString())
                    if (!file.exists()) file.createNewFile()
                    val playerData = YamlConfiguration.loadConfiguration(file)
                    try {
                        playerData.set(key, value)
                        playerData.save(file)
                    } catch (e: Exception) { }
                }
                "mysql", "postgres" -> {
                    val key = key.replace(".", "")
                    try {
                        if (!sqlPlayerColumnExists(player)) sqlCreatePlayerColumn(player)
                        connection.prepareStatement("update player set $key = ? where uuid = '$player'").use { statement ->
                            statement.setString(1, value)
                            statement.execute()
                        }
                    } catch (e: Exception) { throw e }
                }
                else -> throw StorageException("Illegal database type $type")
            }
    }
}