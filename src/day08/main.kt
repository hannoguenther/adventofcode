package day08

fun main() {
    val forest = Forest.parse()
    forest.setupVisibilities()

    val visibleTrees: Int = forest.trees.flatten().count {
        it.isVisible
    }
    println("sum of visible trees: $visibleTrees")


    forest.setupViewingDistances()
    val maxScenicScore: Int = forest.trees.flatten().maxOf { it.scenicScore }
    println("maximum scenic score: $maxScenicScore")
}