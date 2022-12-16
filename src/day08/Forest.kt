package day08

import java.io.File

class Tree(
    val height: Int
) {
    val visibilities = booleanArrayOf(false, false, false, false)

    val isVisible: Boolean
        get() = visibilities.any { it }

    val viewingDistances = intArrayOf(0, 0, 0, 0)

    val scenicScore: Int
        get() = viewingDistances.reduce(Int::times)

    companion object {
        const val IDX_LEFT = 0
        const val IDX_TOP = 1
        const val IDX_RIGHT = 2
        const val IDX_BOTTOM = 3
    }
}


class Forest(val trees: List<List<Tree>>) {

    companion object {
        fun parse(): Forest {
            val content = File("src/day08/input.txt").readLines()
            val trees = content.map { line ->
                line.map {
                    Tree(it.digitToInt())
                }
            }
            return Forest(trees)
        }
    }

    fun setupVisibilities() {
        fun walk(
            direction: Int,
            primaryIndices: IntProgression,
            secondaryIndices: IntProgression,
            treeFinder: (primary: Int, secondary: Int) -> Tree
        ) {
            for (primary in primaryIndices) {
                var maxHeight = Int.MIN_VALUE
                for (secondary in secondaryIndices) {
                    val tree = treeFinder(primary, secondary)
                    if (tree.height > maxHeight) {
                        tree.visibilities[direction] = true
                        maxHeight = tree.height
                    }
                }
            }
        }

        val columnIndices = trees.first().indices
        val rowIndices = trees.indices

        walk(Tree.IDX_LEFT, rowIndices, columnIndices) { row: Int, column: Int ->
            trees[row][column]
        }

        walk(Tree.IDX_RIGHT, rowIndices, columnIndices.reversed()) { row: Int, column: Int ->
            trees[row][column]
        }

        walk(Tree.IDX_TOP, columnIndices, rowIndices) { column: Int, row: Int ->
            trees[row][column]
        }

        walk(Tree.IDX_BOTTOM, columnIndices, rowIndices.reversed()) { column: Int, row: Int ->
            trees[row][column]
        }
    }


    fun setupViewingDistances() {
        fun Tree.walk(
            direction: Int,
            remainingIndices: IntProgression,
            treeFinder: (index: Int) -> Tree
        ) {
            var distance = 0
            for (index in remainingIndices) {
                distance++
                if (treeFinder(index).height >= this.height) break
            }
            this.viewingDistances[direction] = distance
        }

        val columnIndices = trees.first().indices
        val rowIndices = trees.indices

        for (row in rowIndices) {
            for (column in columnIndices) {
                val tree = trees[row][column]

                tree.walk(Tree.IDX_LEFT, column - 1 downTo 0) { index: Int ->
                    trees[row][index]
                }

                tree.walk(Tree.IDX_RIGHT, column + 1..columnIndices.last) { index: Int ->
                    trees[row][index]
                }

                tree.walk(Tree.IDX_TOP, row - 1 downTo 0) { index: Int ->
                    trees[index][column]
                }

                tree.walk(Tree.IDX_BOTTOM, row + 1..rowIndices.last) { index: Int ->
                    trees[index][column]
                }
            }
        }
    }
}