package site.remlit.blueb.hyacinthhello

enum class MessageType {
    JOIN,
    LEAVE,
    DEATH;

    // fallback is always join

    override fun toString(): String {
        return if (super.equals(JOIN)) "joinmsg"
        else if (super.equals(LEAVE)) "leavemsg"
        else if (super.equals(DEATH)) "deathmsg"
        else "joinmsg"
    }

    companion object {
        fun fromString(str: String): MessageType {
            return when (str) {
                "join" -> JOIN
                "joinmsg" -> JOIN
                "leave" -> LEAVE
                "leavemsg" -> LEAVE
                "death" -> DEATH
                "deathmsg" -> DEATH
                else -> JOIN
            }
        }
    }
}
