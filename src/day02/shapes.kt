package day02

sealed interface Shape {
    val value: Int
    infix fun battle(other: Shape): Int
}

object Rock : Shape {
    override val value: Int = 1
    override infix fun battle(other: Shape) = when (other) {
        Rock -> 3
        Paper -> 0
        Scissor -> 6
    }
}

object Paper : Shape {
    override val value: Int = 2
    override infix fun battle(other: Shape) = when (other) {
        Rock -> 6
        Paper -> 3
        Scissor -> 0
    }
}

object Scissor : Shape {
    override val value: Int = 3
    override infix fun battle(other: Shape) = when (other) {
        Rock -> 0
        Paper -> 6
        Scissor -> 3
    }
}