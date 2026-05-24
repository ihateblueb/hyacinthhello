package site.remlit.hyacinthhello.util

import site.remlit.hyacinthhello.HyacinthHello

fun getPrefix(): String {
    val prefix = HyacinthHello.instance.config.get("prefix")?.toString() ?: ""
    return if (prefix.isEmpty()) ""
    else "$prefix "
}