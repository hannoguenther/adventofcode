package day17

import java.util.LinkedList

class Chamber(val debug: Boolean) {
    val rows = LinkedList<Int>()

    var rockCount = 0
        private set

    val size get() = rows.count { it and 0b1111111 > 0 }

    fun moveLeft(rock: Rock): Rock? {
        val moved = rock.moveLeft()?.takeIf(::fits)
        if (debug) {
            if (moved == null) {
                println("Jet of gas pushes rock left, but nothing happens")
            } else {
                println("Jet of gas pushes rock left")
                print(moved)
            }
            println()
        }
        return moved
    }

    fun moveRight(rock: Rock): Rock? {
        val moved = rock.moveRight()?.takeIf(::fits)
        if (debug) {
            if (moved == null) {
                println("Jet of gas pushes rock right, but nothing happens")
            } else {
                println("Jet of gas pushes rock right")
                print(moved)
            }
            println()
        }
        return moved
    }

    fun moveDown(rock: Rock): Rock? {
        val moved = rock.fall().takeIf(::fits)
        if (debug) {
            if (moved == null) {
                println("Rock falls 1 unit, causing it to come to rest")
            } else {
                println("Rock falls 1 unit")
                print(moved)
            }
            println()
        }
        return moved
    }

    fun comeToRest(rock: Rock) {
        // make place if there are not enough chamber rows
        var mergingRock = rock
        while (mergingRock.topIndex < 0) {
            mergingRock = mergingRock.fall()
            rows.add(0, 0)
        }

        // merge rock into chamber rows. rows[0] is the highest chamber row,
        // whereas rows[rows.lastIndex] lays on ground level.
        mergingRock.rows.forEachIndexed { rockIndex, rockRow ->
            val chamberIndex = mergingRock.topIndex + rockIndex
            rows[chamberIndex] = rows[chamberIndex] or rockRow
        }
        rockCount++

        if (debug) {
            println("CHAMBER with $rockCount rocks:")
            for (row in rows) {
                println(row.chamberRow(rockChar = '#'))
            }
            println()
        }
    }

    private fun fits(rock: Rock): Boolean {
        if (rock.bottomIndex > rows.lastIndex) return false // Rocks can't be inserted below ground level

        rock.rows.forEachIndexed { index, rockRow ->
            val chamberRow = rows.getOrNull(rock.topIndex + index)
                ?: return@forEachIndexed
            if (chamberRow and rockRow != 0) return false
        }
        return true
    }

    fun print(rock: Rock) {
        println("ROCK (y=${rock.bottomIndex}):")
        for (row in rock.rows) {
            println(row.chamberRow(rockChar = '@'))
        }
    }

    private fun Int.chamberRow(rockChar: Char = '@', emptyChar: Char = '.', wallChar: Char = '|'): String {
        val inner = Integer.toBinaryString(this)
            .padStart(7, '0')
            .replace('0', emptyChar)
            .replace('1', rockChar)
        return "$wallChar$inner$wallChar"
    }
}