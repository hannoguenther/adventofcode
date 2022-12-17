package day12

import java.io.File

class Node(val row: Int, val column: Int, val name: Char) {
    val isStart = name == 'S'
    val isEnd = name == 'E'
    val elevation: Int = when {
        isStart -> 'a'.code
        isEnd -> 'z'.code
        else -> name.code
    }
    val isLowestElevation: Boolean = elevation == 'a'.code
}


class Graph(val matrix: List<List<Node>>) {
    companion object {
        fun parse(): Graph {
            val content = File("src/day12/input.txt").readLines()
            return Graph(
                matrix = content.mapIndexed { row, line ->
                    line.mapIndexed { column, name ->
                        Node(row, column, name)
                    }
                }
            )
        }
    }

    val nodes: List<Node> = matrix.flatten()

    val start: Node = nodes.first(Node::isStart)
    val end: Node = nodes.first(Node::isEnd)

    fun neighbours(node: Node): List<Node> = listOfNotNull(
        matrix.getOrNull(node.row)?.getOrNull(node.column - 1), // left
        matrix.getOrNull(node.row - 1)?.getOrNull(node.column), // top
        matrix.getOrNull(node.row)?.getOrNull(node.column + 1), // right
        matrix.getOrNull(node.row + 1)?.getOrNull(node.column) // bottom
    )

}