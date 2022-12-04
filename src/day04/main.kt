package day04

import java.io.File

fun main() {
    val content = File("src/day04/input.txt").readLines()
    val pairs: List<Pair<IntRange, IntRange>> = content.map { line ->
        val (left: IntRange, right: IntRange) = line.split(',').map {
            val (from, to) = it.split('-')
            from.toInt()..to.toInt()
        }
        left to right
    }

    infix fun IntRange.isFullyIn(other: IntRange) = first in other && last in other

    fun Pair<IntRange, IntRange>.isOneAssignmentIsFullyContainedInAnother(): Boolean {
        return first isFullyIn second || second isFullyIn first
    }

    val pairsWithFullyContainedAssignments = pairs.count {
        it.isOneAssignmentIsFullyContainedInAnother()
    }
    println("number pairs that are fully contained in one another: $pairsWithFullyContainedAssignments")

    val pairsWithOverlap = pairs.count { (left, right) ->
        (left intersect right).isNotEmpty()
    }
    println("number pairs with overlapping assignments: $pairsWithOverlap")
}