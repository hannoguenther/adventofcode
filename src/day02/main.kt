package day02

import java.io.File


fun main() {
    data class EncodedStrategy(val other: String, val mine: String)

    val encodedStrategies: List<EncodedStrategy> = File("src/day02/input.txt")
        .readLines()
        .map { line ->
            val (other, mine) = line.split(' ')
            EncodedStrategy(other, mine)
        }

    fun pointsOfRockPaperScissorBattle(other: Shape, mine: Shape): Int {
        return mine.value + (mine battle other)
    }

    fun EncodedStrategy.otherShape(): Shape = when (other) {
        "A" -> Rock
        "B" -> Paper
        "C" -> Scissor
        else -> throw IllegalArgumentException("wrong input")
    }

    fun EncodedStrategy.myShape(): Shape = when (mine) {
        "X" -> Rock
        "Y" -> Paper
        "Z" -> Scissor
        else -> throw IllegalArgumentException("wrong input")
    }

    val totalPoints = encodedStrategies.sumOf {
        pointsOfRockPaperScissorBattle(it.otherShape(), it.myShape())
    }
    println("total points of rock paper scissor battles: $totalPoints")


    fun EncodedStrategy.myInferredShape(): Shape {
        val intendedPoints = when (mine) {
            "X" -> 0
            "Y" -> 3
            "Z" -> 6
            else -> throw IllegalArgumentException("wrong input")
        }
        val otherShape = otherShape()
        return listOf(Rock, Paper, Scissor).first { it battle otherShape == intendedPoints }
    }

    val totalInferredPoint = encodedStrategies.sumOf {
        pointsOfRockPaperScissorBattle(it.otherShape(), it.myInferredShape())
    }
    println("total points of rock paper scissor battles with intended results: $totalInferredPoint")
}
