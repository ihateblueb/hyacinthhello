package site.remlit.blueb.hyacinthhello

class Logger {
    companion object {
        private val prefix = "[HyacinthHello]"
        fun info(message: String) = HyacinthHello.instance.logger.info("$prefix $message")
        fun warn(message: String) = HyacinthHello.instance.logger.warning("$prefix $message")
        fun error(message: String) = HyacinthHello.instance.logger.severe("$prefix $message")
    }
}