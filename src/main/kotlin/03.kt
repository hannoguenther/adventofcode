import java.io.File

fun day3() {
    val rucksacks = File("input/03.txt").readLines()

    fun Char.priority() = if (isLowerCase()) {
        1 + code - 'a'.code
    } else {
        27 + code - 'A'.code
    }

    fun List<Set<Char>>.findDuplicateItem(): Char = reduce { accItems: Set<Char>, nextItems: Set<Char> ->
        accItems intersect nextItems
    }.first()

    val duplicateItemsPrioritiesSum = rucksacks.sumOf { rucksackLine ->
        val compartments: List<Set<Char>> = rucksackLine.chunked(rucksackLine.length / 2) { it.toSet() }
        compartments.findDuplicateItem().priority()
    }
    println("priorities sum of duplicate items per rucksack: $duplicateItemsPrioritiesSum")

    val groupBadgePrioritiesSum = rucksacks.chunked(3) { groupLines ->
        val eachElfItemsInGroup: List<Set<Char>> = groupLines.map(String::toSet)
        eachElfItemsInGroup.findDuplicateItem().priority()
    }.sum()
    println("priorities sum of group badges: $groupBadgePrioritiesSum")
}