package day05

import java.io.File

fun main() {
    val header = mutableListOf<String>()
    val operations = mutableListOf<String>()

    var isHeader = true
    File("src/day05/input.txt").forEachLine { line ->
        line.ifBlank {
            isHeader = false
            return@forEachLine
        }
        if (isHeader) {
            header.add(line)
        } else {
            operations.add(line)
        }
    }


    val stackIndexMatches = Regex("\\d+").findAll(header.last()).toList()
    val stackLines: List<String> = header.dropLast(1).reversed()


    val stacks: Map<Int, List<String>> = stackIndexMatches.associateBy(
        keySelector = { it.value.toInt() },
        valueTransform = { matchResult ->
            stackLines
                .map { line ->
                    line.slice(matchResult.range)
                }.filter {
                    it.isNotBlank()
                }
        }
    )

    data class Command(val quantity: Int, val fromStackNumber: Int, val toStackNumber: Int)

    val commands = operations.map { line ->
        val (quantity, fromStack, toStack) = Regex("move (\\d+) from (\\d) to (\\d)").matchEntire(line)!!.destructured
        Command(quantity.toInt(), fromStack.toInt(), toStack.toInt())
    }


    val stacksForTask1: Map<Int, MutableList<String>> = stacks.mapValues { (_, stack) -> stack.toMutableList() }
    for (command in commands) {
        val fromStack = stacksForTask1.getValue(command.fromStackNumber)
        val toStack = stacksForTask1.getValue(command.toStackNumber)
        repeat(command.quantity) {
            toStack.add(fromStack.removeLast())
        }
    }

    val cratesOnTopOfStacksForTask1 = stacksForTask1.values.joinToString("") { it.lastOrNull() ?: "-" }
    println("Task 1 Crates at the top of each stack from left to right: $cratesOnTopOfStacksForTask1")


    val stacksForTask2: Map<Int, MutableList<String>> = stacks.mapValues { (_, stack) -> stack.toMutableList() }
    for (command in commands) {
        val fromStack = stacksForTask2.getValue(command.fromStackNumber)
        val toStack = stacksForTask2.getValue(command.toStackNumber)
        toStack += fromStack.takeLast(command.quantity)
        repeat(command.quantity) {
            fromStack.removeLast()
        }
    }

    val cratesOnTopOfStacksForTask2 = stacksForTask2.values.joinToString("") { it.lastOrNull() ?: "-" }
    println("Task 2 (updated cranes) Crates at the top of each stack from left to right: $cratesOnTopOfStacksForTask2")

}