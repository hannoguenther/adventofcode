import java.io.File

private sealed interface Shape {
    val value: Int
    infix fun battle(other: Shape): Int
}

private object Rock : Shape {
    override val value: Int = 1
    override infix fun battle(other: Shape) = when (other) {
        Rock -> 3
        Paper -> 0
        Scissor -> 6
    }
}

private object Paper : Shape {
    override val value: Int = 2
    override infix fun battle(other: Shape) = when (other) {
        Rock -> 6
        Paper -> 3
        Scissor -> 0
    }
}

private object Scissor : Shape {
    override val value: Int = 3
    override infix fun battle(other: Shape) = when (other) {
        Rock -> 0
        Paper -> 6
        Scissor -> 3
    }
}

private data class EncodedStrategy(val other: String, val mine: String)

private val encodedStrategies: List<EncodedStrategy> = File("input/02.txt")
    .readLines()
    .map { line ->
        val (other, mine) = line.split(' ')
        EncodedStrategy(other, mine)
    }

private fun pointsOfRockPaperScissorBattle(other: Shape, mine: Shape): Int {
    return mine.value + (mine battle other)
}

fun day2() {
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
