package site.remlit.blueb.hyacinthhello.util

import site.remlit.blueb.hyacinthhello.HyacinthHello

fun getPrefix(): String {
    val prefix = HyacinthHello.instance.config.get("prefix")?.toString() ?: ""
    return if (prefix.isEmpty()) ""
    else "$prefix "
}