package day06

import java.io.File

fun main() {
    val content = File("src/day06/input.txt").readText()

    fun findMarkerPosition(markerSize: Int): Int = content
        .windowedSequence(markerSize)
        .indexOfFirst {
            it.toSet().size == it.length // true if all chars are distinct
        } + markerSize


    val markerPosition4 = findMarkerPosition(4)
    println("first start-of-packet marker of size 4: $markerPosition4")


    val markerPosition14 = findMarkerPosition(14)
    println("first start-of-packet marker of size 14: $markerPosition14")
}