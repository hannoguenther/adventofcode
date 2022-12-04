package de.hannoguenther.aoc

import org.junit.Test

import org.junit.Assert.*

class AdventOfCode {

    @Test
    fun addition_isCorrect() {
        val content = this.javaClass.classLoader.getResourceAsStream("aoc1.txt").use {
            it.bufferedReader().readText()
        }
        

        assertEquals(4, 2 + 2)
    }
}
