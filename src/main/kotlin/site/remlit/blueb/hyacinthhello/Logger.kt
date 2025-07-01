package site.remlit.blueb.hyacinthhello

class Logger {
    companion object {
        fun info(message: String) = HyacinthHello.instance.logger.info(message)
        fun warn(message: String) = HyacinthHello.instance.logger.warning(message)
        fun error(message: String) = HyacinthHello.instance.logger.severe(message)
    }
}