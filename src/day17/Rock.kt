package day17

data class Rock(val rows: List<Int>, val bottomIndex: Int = -4) {
    constructor(vararg rows: Int) : this(rows.toList())

    val topIndex get() = bottomIndex - rows.lastIndex

    fun fall() = copy(bottomIndex = bottomIndex + 1)

    fun moveLeft(): Rock? {
        if (rows.any { it and 0b1000000 > 0 }) return null
        return copy(rows = rows.map { it shl 1 })
    }

    fun moveRight(): Rock? {
        if (rows.any { it and 0b0000001 > 0 }) return null
        return copy(rows = rows.map { it shr 1 })
    }
}