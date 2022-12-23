package day17

import java.io.File

val rocks = listOf(
    Rock(
        0b0011110
    ),
    Rock(
        0b0001000,
        0b0011100,
        0b0001000
    ),
    Rock(
        0b0000100,
        0b0000100,
        0b0011100
    ),
    Rock(
        0b0010000,
        0b0010000,
        0b0010000,
        0b0010000
    ),
    Rock(
        0b0011000,
        0b0011000
    )
)

val jetPatterns = File("src/day17/input.txt").readText()

private fun <T> Iterable<T>.asRepeatingSequence(): Sequence<T> = sequence {
    while (true) yieldAll(this@asRepeatingSequence)
}

fun task1() {
    val debug = false
    val endlessRockIterator: Iterator<Rock> = rocks.asRepeatingSequence().iterator()
    var currentRock: Rock = endlessRockIterator.next()

    val chamber = Chamber(debug)

    if (debug) {
        println("The first rock begins falling:")
        chamber.print(currentRock)
        println()
    }

    for (char in jetPatterns.toList().asRepeatingSequence()) {
        currentRock = when (char) {
            '<' -> chamber.moveLeft(currentRock) ?: currentRock
            '>' -> chamber.moveRight(currentRock) ?: currentRock
            else -> throw IllegalArgumentException("wrong movement character")
        }

        val moved = chamber.moveDown(currentRock)
        currentRock = if (moved == null) {
            chamber.comeToRest(currentRock)
            if (chamber.rockCount == 2022) break
            endlessRockIterator.next()
        } else moved
    }

    val chamberSize = chamber.size
    assert(chamberSize == 3068)
    println("chamber size: $chamberSize")
}

fun task2() {
    val perfRocks = 1_000_000_000_000L

    for (i in 1.. perfRocks) {
        if (i % 1_000_000_000L == 0L) {
            println(i)
        }
    }
}

fun main() {
    task1()
    task2()
}